package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.DepartmentBasicValue;
import com.edusyspro.api.dto.custom.DepartmentEssential;
import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.repository.DepartmentRepository;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.interfaces.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UpdateContext updateContext;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, UpdateContext updateContext) {
        this.departmentRepository = departmentRepository;
        this.updateContext = updateContext;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO entity) {
        Long countOccurrence = departmentRepository.departmentExistsByNameOrCode(
                entity.getSchool().getId(), entity.getName(), entity.getCode()
        );

        if (countOccurrence > 0) {
            throw new AlreadyExistException(String.format(
                    "Le département  \"%s\" avec le code \"%s\" existe déjà", entity.getName(), entity.getCode()));
        }

        var addedDepartment = departmentRepository.save(DepartmentDTO.toEntity(entity));

        return DepartmentDTO.fromEntity(addedDepartment);
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
        return departmentRepository.findBasicDepartmentBySchool(UUID.fromString(schoolId)).orElseThrow()
                .stream().map(DepartmentBasicValue::toDTO)
                .toList();
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
    public DepartmentDTO fetchOneByCustomColumn(String columnValue, String schoolId) {
        DepartmentEssential basicDTO = departmentRepository.findDepartmentByCode(
                UUID.fromString(schoolId), columnValue
        ).orElseThrow();
        return basicDTO.toDepartmentDTO();
    }

    @Override
    public DepartmentDTO fetchOneByCustomColumn(String columnValue) {
        return null;
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
    public Map<String, Boolean> update(DepartmentDTO entity, Integer id) {
        return Map.of();
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return updateContext.updateDepartmentField(field.field(), field.value(), id);
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
