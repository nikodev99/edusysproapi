package com.edusyspro.api.repository.spec;

import com.edusyspro.api.dto.custom.ReprimandEssential;
import com.edusyspro.api.dto.filters.ReprimandFilters;
import com.edusyspro.api.model.Reprimand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReprimandSpec {
    private final EntityManager entityManager;

    public ReprimandSpec(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<ReprimandEssential> findReprimandsByStudentId(ReprimandFilters filters, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReprimandEssential> cq = cb.createQuery(ReprimandEssential.class);
        Root<Reprimand> reprimand = cq.from(Reprimand.class);
        cq.select(cb.construct(ReprimandEssential.class,
                reprimand.get("id"),
                reprimand.get("student").get("academicYear").get("id"),
                reprimand.get("student").get("academicYear").get("years"),
                reprimand.get("student").get("student").get("id"),
                reprimand.get("student").get("student").get("personalInfo").get("lastName"),
                reprimand.get("student").get("student").get("personalInfo").get("firstName"),
                reprimand.get("student").get("student").get("personalInfo").get("image"),
                reprimand.get("student").get("student").get("personalInfo").get("reference"),
                reprimand.get("student").get("classe").get("id"),
                reprimand.get("student").get("classe").get("name"),
                reprimand.get("student").get("classe").get("grade").get("section"),
                reprimand.get("reprimandDate"),
                reprimand.get("type"),
                reprimand.get("description"),
                reprimand.get("issuedBy").get("id"),
                reprimand.get("issuedBy").get("firstName"),
                reprimand.get("issuedBy").get("lastName"),
                reprimand.get("issuedBy").get("image"),
                reprimand.get("issuedBy").get("reference"),
                reprimand.get("punishment")
        ));

        List<Predicate> predicates = buildPredicates(filters, reprimand, cb);
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(reprimand.get("reprimandDate")));

        SpecificationUtils.specSorter(pageable, reprimand, cb, cq);

        TypedQuery<ReprimandEssential> query = entityManager.createQuery(cq);
        List<ReprimandEssential> reprimands = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<Reprimand> countRoot = countCq.from(Reprimand.class);
        List<Predicate> countCriteria = buildPredicates(filters, countRoot, cb);
        countCq.select(cb.count(countRoot))
                .where(countCriteria.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countCq).getSingleResult();

        return new PageImpl<>(reprimands, pageable, total);
    }

    private List<Predicate> buildPredicates(ReprimandFilters filters, Root<Reprimand> reprimand, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(reprimand.get("student").get("student").get("id"), filters.studentId()));
        predicates.add(cb.equal(reprimand.get("student").get("academicYear").get("id"), filters.academicYearId()));

        if (filters.classeId() != null) {
            predicates.add(cb.equal(reprimand.get("student").get("classe").get("id"), filters.classeId()));
        }
        if (filters.punishmentType() != null) {
            predicates.add(cb.equal(reprimand.get("punishment").get("type"), filters.punishmentType()));
        }
        if (filters.reprimandType() != null) {
            predicates.add(cb.equal(reprimand.get("type"), filters.reprimandType()));
        }
        if (filters.punishmentStatus() != null) {
            predicates.add(cb.equal(reprimand.get("punishment").get("status"), filters.punishmentStatus()));
        }
        if(filters.reprimandBetween() != null && filters.reprimandBetween().length > 0) {
            var startDate = filters.reprimandBetween()[0];
            var endDate = filters.reprimandBetween()[1];
            predicates.add(cb.between(reprimand.get("reprimandDate"), startDate, endDate));
        }

        return predicates;
    }
}
