package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.repository.CourseRepository;
import com.edusyspro.api.service.interfaces.CourseService;
import org.springframework.beans.BeanUtils;
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
    public CourseDTO save(CourseDTO entity) {
        return null;
    }

    @Override
    public List<CourseDTO> saveAll(List<CourseDTO> entities) {
        return List.of();
    }

    @Override
    public List<CourseDTO> fetchAll() {
        return courseRepository.findAll().stream()
                .map(c -> {
                    CourseDTO dto = new CourseDTO();
                    BeanUtils.copyProperties(c, dto);
                    return dto;
                })
                .toList();
    }

    @Override
    public List<CourseDTO> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<CourseDTO> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<CourseDTO> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<CourseDTO> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<CourseDTO> fetchAllById(Integer id) {
        return List.of();
    }

    @Override
    public List<CourseDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<CourseDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<CourseDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return List.of();
    }

    @Override
    public List<CourseDTO> fetchAllByOtherEntityId(Object... arg) {
        return List.of();
    }

    @Override
    public CourseDTO fetchOneById(Integer id) {
        return null;
    }

    @Override
    public CourseDTO fetchOneById(Integer id, String schoolId) {
        return null;
    }

    @Override
    public CourseDTO fetchOneById(Integer id, Object... args) {
        return null;
    }

    @Override
    public CourseDTO fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public CourseDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public CourseDTO fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(CourseDTO entity) {
        return 0;
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(CourseDTO entity) {
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
