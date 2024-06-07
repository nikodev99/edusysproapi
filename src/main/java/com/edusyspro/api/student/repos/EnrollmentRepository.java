package com.edusyspro.api.student.repos;

import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.student.models.EnrolledStudent;
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
    @Query(value = "update EnrollmentEntity set isArchived = ?1 where studentEntity.id = ?2")
    int updateEnrollmentByStudentId(boolean isArchived, UUID uuid);

    @Query("select new com.edusyspro.api.student.models.EnrolledStudent(" +
            "e.studentEntity.id, e.studentEntity.firstName, e.studentEntity.lastName, " +
            "e.studentEntity.gender, e.studentEntity.emailId, e.studentEntity.birthDate, " +
            "e.studentEntity.birthCity, e.studentEntity.nationality, e.studentEntity.reference, " +
            "e.studentEntity.image, e.classeEntity.name) " +
            "from EnrollmentEntity e where e.school.id = ?1")
    List<EnrolledStudent> findEnrolledStudent(UUID schoolId);

}
