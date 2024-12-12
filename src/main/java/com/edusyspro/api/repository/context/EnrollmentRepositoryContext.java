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
public class EnrollmentRepositoryContext {

    private final EntityManager entityManager;

    @Value("${spring.profiles.active[0]}")
    private String activeProfile;

    public EnrollmentRepositoryContext(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<EnrolledStudent> findStudentRandomClassmateByClasseId(UUID schoolId, UUID studentId, int classeId, Pageable pageable) {
        final String statement = getStatement();
        TypedQuery<EnrolledStudent> query = entityManager.createQuery(statement, EnrolledStudent.class)
                .setParameter(1, schoolId)
                .setParameter(2, studentId)
                .setParameter(3, classeId);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<EnrolledStudent> enrolledStudents = query.getResultList();

        return new PageImpl<>(enrolledStudents, pageable, enrolledStudents.size());
    }

    private String getStatement() {
        String randomFunction = activeProfile.contains("mysql") ? "rand()" : "random()";

        return "select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.academicYear, e.student.personalInfo.id, e.student.personalInfo.firstName, "+
                "e.student.personalInfo.lastName, e.student.personalInfo.gender, e.student.personalInfo.emailId, e.student.personalInfo.birthDate, "+
                "e.student.personalInfo.birthCity, e.student.personalInfo.nationality, e.student.reference, e.student.personalInfo.image, "+
                "e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.classe.monthCost, "+
                "e.student.dadName, e.student.momName) from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.student.id <> ?2 and e.classe.id = ?3 " +
                "and e.academicYear.current = true and e.isArchived = false order by " + randomFunction;
    }
}
