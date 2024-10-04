package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.Student;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.Guardian;
import com.edusyspro.api.dto.GuardianEssential;
import com.edusyspro.api.repository.GuardianRepository;
import com.edusyspro.api.repository.StudentRepository;
import com.edusyspro.api.service.interfaces.GuardianService;
import com.edusyspro.api.service.interfaces.StudentService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public GuardianEntity saveOrUpdateGuardian(GuardianEntity guardianEntity) {
        if (guardianEntity.getId() == null) {
            return guardianRepository.save(guardianEntity);
        }else {
            return entityManager.merge(guardianEntity);
        }
    }

    @Override
    public Guardian findGuardianById(String id) {
        GuardianEssential essential = guardianRepository.findGuardianEntityById(UUID.fromString(id));
        Guardian guardian = new Guardian();
        if (essential != null)
            guardian = GuardianEssential.populateGuardian(essential);

        return guardian;
    }

    @Override
    public Guardian findGuardianByIdWithStudents(String guardianId) {
        Guardian guardian = findGuardianById(guardianId);
        List<Student> student = studentService.findStudentByGuardian(guardianId);
        guardian.setStudentEntity(student);
        return guardian;
    }

    @Override
    public List<Guardian> findAll() {
        List<GuardianEssential> allGuardians = guardianRepository.findAllGuardians();
        List<Guardian> guardians = new ArrayList<>();
        if (!allGuardians.isEmpty())
            guardians = allGuardians.stream()
                    .map(GuardianEssential::populateGuardian)
                    .toList();

        return guardians;
    }
}
