package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.custom.GuardianEssential;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.repository.GuardianRepository;
import com.edusyspro.api.service.interfaces.GuardianService;
import com.edusyspro.api.service.interfaces.StudentService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GuardianServiceImp implements GuardianService {

    private final EntityManager entityManager;

    private final GuardianRepository guardianRepository;
    private final StudentService studentService;

    @Autowired
    public GuardianServiceImp(
            EntityManager entityManager,
            GuardianRepository guardianRepository,
            StudentService studentService
    ) {
        this.entityManager = entityManager;
        this.guardianRepository = guardianRepository;
        this.studentService = studentService;
    }

    @Transactional
    public GuardianEntity saveOrUpdateGuardian(GuardianEntity guardianEntity, String reference) {
        if (guardianEntity.getId() == null) {
            if (reference != null) {
                Individual guardianPersonalInfo = guardianEntity.getPersonalInfo();
                guardianPersonalInfo.setReference(reference);
            }
            return guardianRepository.save(guardianEntity);
        }else {
            return entityManager.getReference(GuardianEntity.class, guardianEntity.getId());
        }
    }

    @Override
    public GuardianDTO findGuardianById(String id) {
        return guardianRepository.findGuardianEntityById(UUID.fromString(id))
                .map(GuardianEssential::populateGuardian)
                .orElseGet(GuardianDTO::new);
    }

    @Override
    public GuardianDTO findGuardianByIdWithStudents(String schoolId, String guardianId) {
        GuardianDTO guardianDTO = findGuardianById(guardianId);
        List<StudentDTO> studentDTO = studentService.findStudentByGuardian(schoolId, guardianId);
        guardianDTO.setStudentDTOS(studentDTO);
        return guardianDTO;
    }

    @Override
    public UUID getGuardianId(Long personalInfoId) {
        return guardianRepository.getGuardianByPersonalInfo(personalInfoId).orElseThrow(
                () -> new NotFountException("Tuteur introuvable"));
    }

    @Override
    public List<GuardianDTO> searchAll(String searchInput) {
        return guardianRepository.findAllGuardians(searchInput)
                .stream()
                .map(GuardianEssential::populateGuardian)
                .toList();
    }
}
