package com.edusyspro.api.repository.spec;

import com.edusyspro.api.dto.custom.AssignmentEssential;
import com.edusyspro.api.dto.filters.AssignmentFilter;
import com.edusyspro.api.model.Assignment;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AssignmentSpec {

    private final EntityManager entityManager;

    public AssignmentSpec(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<AssignmentEssential> findAllSearchAndFilteredAssignments(AssignmentFilter filters, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssignmentEssential> cq = cb.createQuery(AssignmentEssential.class);
        Root<Assignment> assignment = cq.from(Assignment.class);
        cq.select(cb.construct(
                AssignmentEssential.class,
                assignment.get("id"),
                assignment.get("semester").get("semestre").get("semesterName"),
                assignment.get("exam").get("examType"),
                assignment.get("preparedBy").get("id"),
                assignment.get("preparedBy").get("firstName"),
                assignment.get("preparedBy").get("lastName"),
                assignment.get("preparedBy").get("image"),
                assignment.get("classeEntity").get("id"),
                assignment.get("classeEntity").get("name"),
                assignment.get("classeEntity").get("grade").get("section"),
                assignment.get("subject").get("id"),
                assignment.get("subject").get("course"),
                assignment.get("subject").get("abbr"),
                assignment.get("examName"),
                assignment.get("examDate"),
                assignment.get("startTime"),
                assignment.get("endTime"),
                assignment.get("passed"),
                assignment.get("addedDate"),
                assignment.get("updatedDate")
        ));

        List<Predicate> dataCriteria = buildPredicates(filters, assignment, cb);
        cq.where(dataCriteria.toArray(new Predicate[0]));

        TypedQuery<AssignmentEssential> query = entityManager.createQuery(cq);
        List<AssignmentEssential> assignments = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<Assignment> countRoot = countCq.from(Assignment.class);
        List<Predicate> countCriteria = buildPredicates(filters, countRoot, cb);
        countCq.select(cb.count(countRoot))
                .where(countCriteria.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countCq).getSingleResult();

        return new PageImpl<>(assignments, pageable, total);
    }

    private List<Predicate> buildPredicates(AssignmentFilter filters, Root<Assignment> assignment, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(
                assignment.get("semester").get("semestre").get("academicYear").get("id"),
                filters.academicYearId()));

        if (filters.planningId()   != null) {
            predicates.add(cb.equal(assignment.get("semester").get("id"), filters.planningId()));
        }
        if (filters.gradeId()      != null) {
            predicates.add(cb.equal(assignment.get("classeEntity").get("grade").get("id"), filters.gradeId()));
        }
        if (filters.semesterId()   != null) {
            predicates.add(cb.equal(assignment.get("semester").get("semester").get("semesterId"), filters.semesterId()));
        }
        if (filters.classeId()      != null) {
            predicates.add(cb.equal(assignment.get("classeEntity").get("id"), filters.classeId()));
        }
        if (filters.courseId()    != null) {
            predicates.add(cb.equal(assignment.get("subject").get("id"), filters.courseId()));
        }

        if (StringUtils.hasText(filters.search())) {
            String pattern = "%" + filters.search().toLowerCase() + "%";
            Predicate p1 = cb.like(cb.lower(assignment.get("preparedBy").get("firstName")), pattern);
            Predicate p2 = cb.like(cb.lower(assignment.get("preparedBy").get("lastName")), pattern);
            Predicate p3 = cb.like(cb.lower(assignment.get("subject").get("course")), pattern);
            Predicate p4 = cb.like(cb.lower(assignment.get("examName")), pattern);
            Predicate p5 = cb.like(cb.lower(assignment.get("classeEntity").get("name")), pattern);

            predicates.add(cb.or(p1, p2, p3, p4, p5));
        }

        return predicates;
    }
}
