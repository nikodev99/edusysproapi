package com.edusyspro.api.student.repos;

import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "update EnrollmentEntity set isArchived = ?1 where student.id = ?2")
    int updateEnrollmentByStudentId(boolean isArchived, UUID uuid);

    @Query("select new com.edusyspro.api.student.models.dtos.EnrolledStudent(e.student.id, e.student.firstName, e.student.lastName, " +
            "e.student.gender, e.student.emailId, e.student.birthDate, e.student.birthCity, e.student.nationality, e.student.reference, " +
            "e.student.image, e.classe.name) from EnrollmentEntity e where e.school.id = ?1")
    List<EnrolledStudent> findEnrolledStudent(UUID schoolId);
}
