package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.CourseBasicValue;
import com.edusyspro.api.dto.custom.CourseEssential;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.repository.CourseRepository;
import com.edusyspro.api.service.interfaces.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseDTO save(CourseDTO entity) {
        boolean alreadyExists = courseAlreadyExists(entity);
        if(alreadyExists) {
            throw new AlreadyExistException("La matière " + entity.getCourse() + " existe déjà");
        }
        courseRepository.save(CourseDTO.toEntity(entity));
        return entity;
    }

    @Override
    public List<CourseDTO> saveAll(List<CourseDTO> entities) {
        return List.of();
    }

    @Override
    public List<CourseDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<CourseDTO> fetchAll(String schoolId) {
        return courseRepository.findAllCourses(UUID.fromString(schoolId)).stream()
                .map(CourseBasicValue::toCourse)
                .toList();
    }

    @Override
    public Page<CourseDTO> fetchAll(String schoolId, Pageable pageable) {
        return courseRepository.findAllCoursesBySchoolId(UUID.fromString(schoolId), pageable)
                .map(CourseEssential::toCourse);
    }

    @Override
    public List<CourseDTO> fetchAll(Object... args) {
        var schoolId = UUID.fromString(String.valueOf(args[0]));
        var courseName = "%" + args[1].toString() + "%";
        return courseRepository.findAllCoursesBySchoolId(schoolId, courseName).stream()
                .map(CourseEssential::toCourse)
                .toList();
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
        return courseRepository.findCourseById(id).toCourse();
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
    public Map<String, Boolean> update(CourseDTO entity, Integer id) {
        return Map.of();
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

    private boolean courseAlreadyExists(CourseDTO entity) {
        return courseRepository.existsCourseByCourseAndAbbr(
          entity.getCourse(),
          entity.getAbbr()
        );
    }
}
