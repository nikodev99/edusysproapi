package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Modifying
    @Transactional
    @Query(value = "update Enrollment set isArchived = ?1 where student.id = ?2")
    int updateEnrollmentByStudentId(boolean isArchived, UUID uuid);

}
