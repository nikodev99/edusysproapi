package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.dto.custom.GradeBasicValue;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.repository.GradeRepository;
import com.edusyspro.api.service.interfaces.GradeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GardeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GardeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public GradeDTO save(GradeDTO entity) {
        return null;
    }

    @Override
    public List<GradeDTO> saveAll(List<GradeDTO> entities) {
        return List.of();
    }

    @Override
    public List<GradeDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<GradeDTO> fetchAll(String schoolId) {
        return gradeRepository.findAllGradeBySchool(UUID.fromString(schoolId)).stream()
                .map(GradeBasicValue::convertToDTO)
                .toList();
    }

    @Override
    public Page<GradeDTO> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<GradeDTO> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<GradeDTO> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<GradeDTO> fetchAllById(Integer id) {
        return List.of();
    }

    @Override
    public List<GradeDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<GradeDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<GradeDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return List.of();
    }

    @Override
    public List<GradeDTO> fetchAllByOtherEntityId(Object... arg) {
        return List.of();
    }

    @Override
    public GradeDTO fetchOneById(Integer id) {
        return null;
    }

    @Override
    public GradeDTO fetchOneById(Integer id, String schoolId) {
        return null;
    }

    @Override
    public GradeDTO fetchOneById(Integer id, Object... args) {
        return null;
    }

    @Override
    public GradeDTO fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public GradeDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public GradeDTO fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(GradeDTO entity) {
        return 0;
    }

    @Override
    public Map<String, Boolean> update(GradeDTO entity, Integer id) {
        return Map.of();
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(GradeDTO entity) {
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
