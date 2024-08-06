package com.edusyspro.api.repository;

import com.edusyspro.api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course getCourseByAbbrContainingIgnoreCase(String abbr);

}
