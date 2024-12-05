package com.edusyspro.api.repository;

import com.edusyspro.api.model.CourseProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseProgramRepository extends JpaRepository<CourseProgram, Long> {
}
