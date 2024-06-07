package com.edusyspro.api.student.repos;

import com.edusyspro.api.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    StudentEntity getStudentById(UUID uuid);

}
