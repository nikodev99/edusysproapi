package com.edusyspro.api.repository.context;

import com.edusyspro.api.dto.custom.EnrolledStudent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RepositoryContext {

    private final EntityManager entityManager;

    @Value("${spring.profiles.active[0]}")
    private String activeProfile;

    public RepositoryContext(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<EnrolledStudent> findStudentRandomClassmateByClasseId(UUID studentId, int classeId, Pageable pageable, boolean exclude) {
        final String statement = getStatement(exclude);
        System.out.println("statement: " + statement);
        TypedQuery<EnrolledStudent> query = entityManager.createQuery(statement, EnrolledStudent.class);

        if (exclude && studentId != null) {
            query.setParameter(1, studentId);
            query.setParameter(2, classeId);
        }else {
            query.setParameter(1, classeId);
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<EnrolledStudent> enrolledStudents = query.getResultList();

        return new PageImpl<>(enrolledStudents, pageable, enrolledStudents.size());
    }

    private String getStatement(boolean exclude) {
        String randomFunction = activeProfile.contains("mysql") ? "rand()" : "random()";

        return "select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear," +
                "e.student.reference, e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section," +
                "e.classe.monthCost, e.student.dadName, e.student.momName) from EnrollmentEntity e where " +
                (exclude ?  "e.student.id <> ?1 and " : "") + "e.classe.id = ?" + (exclude ? "2" : "1") +
                " and e.academicYear.current = true and e.isArchived = false order by " + randomFunction;
    }

    /*private String findAcademicYearStatement() {
        String extractYear = activeProfile.contains("mysql") ? "year()" : "year()";
        return "select "
    }*/
}
