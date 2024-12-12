package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.CourseProgramDTO;
import com.edusyspro.api.dto.custom.CourseProgramBasic;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.repository.CourseProgramRepository;
import com.edusyspro.api.service.interfaces.CourseProgramService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CourseProgramImpl implements CourseProgramService {

    private final CourseProgramRepository courseProgramRepository;

    public CourseProgramImpl(CourseProgramRepository courseProgramRepository) {
        this.courseProgramRepository = courseProgramRepository;
    }

    @Override
    public CourseProgramDTO save(CourseProgramDTO entity) {
        return null;
    }

    @Override
    public List<CourseProgramDTO> saveAll(List<CourseProgramDTO> entities) {
        return List.of();
    }

    @Override
    public List<CourseProgramDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<CourseProgramDTO> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<CourseProgramDTO> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<CourseProgramDTO> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<CourseProgramDTO> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<CourseProgramDTO> fetchAllById(Long id) {
        return List.of();
    }

    @Override
    public List<CourseProgramDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<CourseProgramDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<CourseProgramDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return courseProgramRepository
                .findBasicTeacherCoursePrograms(UUID.fromString(otherEntityId)).stream()
                .map(CourseProgramBasic::toDTO)
                .toList();
    }

    @Override
    public List<CourseProgramDTO> fetchAllByOtherEntityId(Object... arg) {
        return List.of();
    }

    @Override
    public CourseProgramDTO fetchOneById(Long id) {
        return null;
    }

    @Override
    public CourseProgramDTO fetchOneById(Long id, String schoolId) {
        return null;
    }

    @Override
    public CourseProgramDTO fetchOneById(Long id, Object... args) {
        return null;
    }

    @Override
    public CourseProgramDTO fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public CourseProgramDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public CourseProgramDTO fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(CourseProgramDTO entity) {
        return 0;
    }

    @Override
    public int patch(Long id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(CourseProgramDTO entity) {
        return 0;
    }

    @Override
    public Map<String, Long> count(Long id) {
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
