package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.TeacherBossDTO;
import com.edusyspro.api.dto.custom.TeacherBossEssential;
import com.edusyspro.api.repository.ClasseTeacherBossRepository;
import com.edusyspro.api.service.interfaces.ClasseTeacherBossService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClasseTeacherBossServiceImpl implements ClasseTeacherBossService {

    private final ClasseTeacherBossRepository classeTeacherBossRepository;

    public ClasseTeacherBossServiceImpl(ClasseTeacherBossRepository classeTeacherBossRepository) {
        this.classeTeacherBossRepository = classeTeacherBossRepository;
    }

    @Override
    public Page<TeacherBossDTO> fetchAllTeacherBoss(int classeId, Pageable pageable) {
        return classeTeacherBossRepository.findAllTeacherBossByClasse(classeId, pageable)
                .map(TeacherBossEssential::toDTO);
    }

    @Override
    public List<TeacherBossDTO> fetchAllTeacherBoss(int classeId, String academicYearId) {
        return classeTeacherBossRepository.findTeacherBossByClasseId(
                classeId, UUID.fromString(academicYearId)
        ).stream().map(TeacherBossEssential::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherBossDTO fetchTeacherBoss(int classeId) {
         return classeTeacherBossRepository.findCurrentTeacherBoss(classeId)
                 .map(TeacherBossEssential::toDTO)
                 .orElse(null);
    }
}
