package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.custom.ClassBasicValue;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.service.interfaces.ClasseServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ClasseServiceImp implements ClasseServiceInterface {
    private final ClasseRepository classeRepository;

    @Override
    public ClasseDTO save(ClasseDTO entity) {
        return null;
    }

    @Override
    public List<ClasseDTO> saveAll(List<ClasseDTO> entities) {
        return List.of();
    }

    @Override
    public List<ClasseDTO> fetchAll() {
        List<ClasseEntity> classeEntity = classeRepository.findAll();
        return classeEntity.stream()
                .map(entity -> {
                    ClasseDTO classeDTO = new ClasseDTO();
                    BeanUtils.copyProperties(entity, classeDTO);
                    return classeDTO;
                })
                .toList();
    }

    @Override
    public List<ClasseDTO> fetchAll(String schoolId) {
        return classeRepository.findAllBasicValue(UUID.fromString(schoolId)).stream()
                .map(ClassBasicValue::toClasse)
                .toList();
    }

    @Override
    public Page<ClasseDTO> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<ClasseDTO> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<ClasseDTO> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<ClasseDTO> fetchAllById(Integer id) {
        return List.of();
    }

    @Override
    public List<ClasseDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<ClasseDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<ClasseDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return List.of();
    }

    @Override
    public List<ClasseDTO> fetchAllByOtherEntityId(Object... arg) {
        return List.of();
    }

    @Override
    public ClasseDTO fetchOneById(Integer id) {
        return null;
    }

    @Override
    public ClasseDTO fetchOneById(Integer id, String schoolId) {
        return null;
    }

    @Override
    public ClasseDTO fetchOneById(Integer id, Object... args) {
        return null;
    }

    @Override
    public ClasseDTO fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public ClasseDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public ClasseDTO fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(ClasseDTO entity) {
        return 0;
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(ClasseDTO entity) {
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
