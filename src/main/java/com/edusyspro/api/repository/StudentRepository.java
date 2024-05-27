package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Student getStudentById(UUID uuid);

}
