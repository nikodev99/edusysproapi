package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.StudentBossDTO;
import com.edusyspro.api.dto.custom.StudentBossEssential;
import com.edusyspro.api.repository.ClasseStudentBossRepository;
import com.edusyspro.api.service.interfaces.ClasseStudentBossService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClasseStudentBossServiceImpl implements ClasseStudentBossService {
    private final ClasseStudentBossRepository classeStudentBossRepository;

    public ClasseStudentBossServiceImpl(ClasseStudentBossRepository classeStudentBossRepository) {
        this.classeStudentBossRepository = classeStudentBossRepository;
    }

    @Override
    public Page<StudentBossDTO> fetchAllStudentBoss(int classeId, Pageable pageable) {
        return classeStudentBossRepository.findAllStudentBossByClasse(classeId, pageable)
                .map(StudentBossEssential::toDTO);
    }

    @Override
    public List<StudentBossDTO> fetchAllStudentBoss(int classeId, String academicYearId) {
        return classeStudentBossRepository.findStudentBossByClasseId(classeId, UUID.fromString(academicYearId)).stream()
                .map(StudentBossEssential::toDTO)
                .toList();
    }

    @Override
    public StudentBossDTO fetchStudentBoss(int classeId) {
        return classeStudentBossRepository.findCurrentStudentBoss(classeId)
                .map(StudentBossEssential::toDTO)
                .orElse(null);
    }
}
