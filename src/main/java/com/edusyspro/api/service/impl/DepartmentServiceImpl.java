package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.DepartmentEssential;
import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.repository.DepartmentRepository;
import com.edusyspro.api.service.interfaces.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO entity) {
        return null;
    }

    @Override
    public List<DepartmentDTO> saveAll(List<DepartmentDTO> entities) {
        return List.of();
    }

    @Override
    public List<DepartmentDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<DepartmentDTO> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<DepartmentDTO> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<DepartmentDTO> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<DepartmentDTO> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<DepartmentDTO> fetchAllById(Integer id) {
        return List.of();
    }

    @Override
    public List<DepartmentDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<DepartmentDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<DepartmentDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return List.of();
    }

    @Override
    public List<DepartmentDTO> fetchAllByOtherEntityId(Object... arg) {
        return List.of();
    }

    @Override
    public DepartmentDTO fetchOneById(Integer id) {
        return null;
    }

    @Override
    public DepartmentDTO fetchOneById(Integer id, String schoolId) {
        return null;
    }

    @Override
    public DepartmentDTO fetchOneById(Integer id, Object... args) {
        return null;
    }

    @Override
    public DepartmentDTO fetchOneByCustomColumn(String columnValue) {
        DepartmentEssential basicDTO = departmentRepository.findDepartmentByCode(columnValue).orElseThrow();
        return basicDTO.toDepartmentDTO();
    }

    @Override
    public DepartmentDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public DepartmentDTO fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(DepartmentDTO entity) {
        return 0;
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(DepartmentDTO entity) {
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
