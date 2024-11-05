package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.repository.CourseRepository;
import com.edusyspro.api.service.interfaces.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course save(Course entity) {
        return null;
    }

    @Override
    public List<Course> saveAll(List<Course> entities) {
        return List.of();
    }

    @Override
    public List<Course> fetchAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<Course> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Course> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<Course> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<Course> fetchAllById(Integer id) {
        return List.of();
    }

    @Override
    public List<Course> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Course fetchOneById(Integer id) {
        return null;
    }

    @Override
    public Course fetchOneById(Integer id, String schoolId) {
        return null;
    }

    @Override
    public Course fetchOneById(Integer id, Object... args) {
        return null;
    }

    @Override
    public int update(Course entity) {
        return 0;
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(Course entity) {
        return 0;
    }

    @Override
    public Map<String, Long> count(Integer id) {
        return Map.of();
    }

    @Override
    public Map<String, Long> count(String schoolId) {
        return Map.of();
    }

    @Override
    public Map<String, Long> count(Object... args) {
        return Map.of();
    }
}
