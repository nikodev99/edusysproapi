package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.Settings;
import com.edusyspro.api.repository.SettingRepo;
import com.edusyspro.api.service.interfaces.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingRepo settingRepo;

    @Autowired
    public SettingsServiceImpl(SettingRepo settingRepo) {
        this.settingRepo = settingRepo;
    }

    @Override
    public Settings save(Settings entity) {
        if (entity.getId() == null) {
            boolean exists = settingRepo.existsByTenantIdAndTitle(entity.getTenantId(), entity.getTitle());
            if (exists)
                throw new AlreadyExistException("Le paramètre avec the titre " + entity.getTitle() + " existe déjà");

            return settingRepo.save(entity);
        }

        Settings existing = settingRepo.findById(entity.getId()).orElseThrow(() -> new NotFountException("Paramètre introuvable"));
        int updatedRow = 0;

        if (!existing.getTenantId().equals(entity.getTenantId()))
            throw new SecurityException("Le paramètre n'est pas accessible");

        if (!Objects.equals(existing.getValue(), entity.getValue())) {
            updatedRow = settingRepo.updateByTenantIdAndTitle(entity.getTenantId(), entity.getTitle(), entity.getValue());
        }

        return updatedRow > 0 ? entity : existing;
    }

    @Override
    public List<Settings> saveAll(List<Settings> entities) {
        return List.of();
    }

    @Override
    public List<Settings> fetchAll() {
        return List.of();
    }

    @Override
    public List<Settings> fetchAll(String schoolId) {
        return settingRepo.findAllByTenantId(UUID.fromString(schoolId));
    }

    @Override
    public Page<Settings> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Settings> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<Settings> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<Settings> fetchAllById(Long id) {
        return List.of();
    }

    @Override
    public List<Settings> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<Settings> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Settings> fetchAllByOtherEntityId(String otherEntityId) {
        return List.of();
    }

    @Override
    public List<Settings> fetchAllByOtherEntityId(Object... arg) {
        return List.of();
    }

    @Override
    public Settings fetchOneById(Long id) {
        return null;
    }

    @Override
    public Settings fetchOneById(Long id, String schoolId) {
        return null;
    }

    @Override
    public Settings fetchOneById(Long id, Object... args) {
        return null;
    }

    @Override
    public Settings fetchOneByCustomColumn(String columnValue, String schoolId) {
        return settingRepo.findByTenantIdAndTitle(UUID.fromString(schoolId), columnValue);
    }

    @Override
    public Settings fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public Settings fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public Settings fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(Settings entity) {
        return 0;
    }

    @Override
    public Map<String, Boolean> update(Settings entity, Long id) {
        return Map.of();
    }

    @Override
    public int patch(Long id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(Settings entity) {
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
