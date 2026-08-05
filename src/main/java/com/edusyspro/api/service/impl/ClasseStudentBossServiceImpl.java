package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.StudentBossDTO;
import com.edusyspro.api.dto.custom.StudentBossEssential;
import com.edusyspro.api.model.ClasseStudentBoss;
import com.edusyspro.api.repository.ClasseStudentBossRepository;
import com.edusyspro.api.service.interfaces.ClasseBossService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClasseStudentBossServiceImpl implements ClasseBossService<StudentBossDTO> {
    private final ClasseStudentBossRepository classeStudentBossRepository;

    public ClasseStudentBossServiceImpl(ClasseStudentBossRepository classeStudentBossRepository) {
        this.classeStudentBossRepository = classeStudentBossRepository;
    }

    @Override
    public int saveClasseBoss(StudentBossDTO classeBossDTO) {
        if (!isStudentInClasse(classeBossDTO.getClasse().getId(), classeBossDTO.getPrincipalStudent().getId()))
            throw new RuntimeException("Cet étudiant n'est pas dans cette classe");

        classeStudentBossRepository.fetchAllBossesIdByClasseId(classeBossDTO.getClasse().getId())
                .forEach(id -> classeStudentBossRepository.inactivateClasseStudentBoss(id, LocalDate.now()));

        ClasseStudentBoss bossToAdd = StudentBossDTO.toEntity(classeBossDTO);
        ClasseStudentBoss boss = classeStudentBossRepository.save(bossToAdd);

        return boss.getId();
    }

    @Override
    public Page<StudentBossDTO> fetchAllClasseBosses(int classeId, Pageable pageable) {
        return classeStudentBossRepository.findAllStudentBossByClasse(classeId, pageable)
                .map(StudentBossEssential::toDTO);
    }

    @Override
    public List<StudentBossDTO> fetchAllClasseBosses(int classeId, String academicYearId) {
        return classeStudentBossRepository.findStudentBossByClasseId(classeId, UUID.fromString(academicYearId)).stream()
                .map(StudentBossEssential::toDTO)
                .toList();
    }

    @Override
    public StudentBossDTO fetchCurrentClasseBoss(int classeId) {
        return classeStudentBossRepository.findCurrentStudentBoss(classeId)
                .map(StudentBossEssential::toDTO)
                .orElse(null);
    }

    @Override
    public boolean checkPrincipal(Object... args) {
        return false;
    }

    private Boolean isStudentInClasse(int classeId, UUID studentId) {
        return classeStudentBossRepository.findStudentInClasse(classeId, studentId).isPresent();
    }
}
