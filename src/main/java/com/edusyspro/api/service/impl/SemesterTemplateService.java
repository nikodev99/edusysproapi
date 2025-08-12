package com.edusyspro.api.service.impl;

import com.edusyspro.api.model.SemesterTemplate;
import com.edusyspro.api.repository.SemesterTemplateRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SemesterTemplateService {
    private final SemesterTemplateRepository semesterTemplateRepository;
    private final EntityManager entityManager;

    public SemesterTemplateService(SemesterTemplateRepository semesterTemplateRepository, EntityManager entityManager) {
        this.semesterTemplateRepository = semesterTemplateRepository;
        this.entityManager = entityManager;
    }

    public SemesterTemplate saveOrUpdate(SemesterTemplate semesterTemplate) {
        if (semesterTemplate.getId() == null) {
            return semesterTemplateRepository.save(semesterTemplate);
        }else {
            return entityManager.merge(semesterTemplate);
        }
    }

    public List<SemesterTemplate> saveAll(List<SemesterTemplate> semesterTemplates) {
        semesterTemplates.forEach(this::saveOrUpdate);
        return semesterTemplates;
    }

    public List<SemesterTemplate> fetchAll(String SchoolId) {
        return semesterTemplateRepository.findAllBySchoolId(UUID.fromString(SchoolId));
    }
}
