package com.edusyspro.api.service.mod;

import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.repository.CourseRepository;
import com.edusyspro.api.service.impl.CourseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends CourseServiceImpl {

    public CourseService(CourseRepository courseRepository) {
        super(courseRepository);
    }

    public List<CourseDTO> findAllBasicCourses(String schoolId) {
        return fetchAll(schoolId);
    }

    public Page<CourseDTO> findAllSchoolCourses(String schoolId, Pageable pageable) {
        return fetchAll(schoolId, pageable);
    }

    public List<CourseDTO> findAllSchoolCourses(String schoolId, String courseName) {
        return fetchAll(schoolId, courseName);
    }

    public CourseDTO findCourse(int courseId) {
        return fetchOneById(courseId);
    }

}
