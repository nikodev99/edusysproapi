package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.TeacherBossDTO;
import com.edusyspro.api.dto.custom.TeacherBossEssential;
import com.edusyspro.api.model.ClasseTeacherBoss;
import com.edusyspro.api.repository.ClasseTeacherBossRepository;
import com.edusyspro.api.service.interfaces.ClasseBossService;
import com.edusyspro.api.utils.Datetime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClasseTeacherBossServiceImpl implements ClasseBossService<TeacherBossDTO> {

    private final ClasseTeacherBossRepository classeTeacherBossRepository;

    public ClasseTeacherBossServiceImpl(ClasseTeacherBossRepository classeTeacherBossRepository) {
        this.classeTeacherBossRepository = classeTeacherBossRepository;
    }

    @Override
    public int saveClasseBoss(TeacherBossDTO classeBossDTO) {
        if (!isTeacherInClasse(classeBossDTO.getClasse().getId(), classeBossDTO.getPrincipalTeacher().getId()))
            throw new RuntimeException("Cet enseignant n'enseigne pas dans cette classe");

        if (checkPrincipal(classeBossDTO.getPrincipalTeacher().getId(), classeBossDTO.getAcademicYear().getId()))
            throw new RuntimeException("Cet enseignant est déjà principal dans une autre classe de cette école");

        classeTeacherBossRepository.fetchAllBossesIdByClasseId(classeBossDTO.getClasse().getId())
                .forEach(id -> classeTeacherBossRepository.inactivateClasseTeacherBoss(id, Datetime.brazzavilleDatetime().toLocalDate()));

        ClasseTeacherBoss bossToAdd = TeacherBossDTO.toEntity(classeBossDTO);
        ClasseTeacherBoss boss = classeTeacherBossRepository.save(bossToAdd);

        return boss.getId();
    }

    @Override
    public Page<TeacherBossDTO> fetchAllClasseBosses(int classeId, Pageable pageable) {
        return classeTeacherBossRepository.findAllTeacherBossByClasse(classeId, pageable)
                .map(TeacherBossEssential::toDTO);
    }

    @Override
    public List<TeacherBossDTO> fetchAllClasseBosses(int classeId, String schoolId) {
        return classeTeacherBossRepository.findTeacherBossByClasseId(
                classeId, UUID.fromString(schoolId)
        ).stream().map(TeacherBossEssential::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherBossDTO fetchCurrentClasseBoss(int classeId) {
        return classeTeacherBossRepository.findCurrentTeacherBoss(classeId)
                .map(TeacherBossEssential::toDTO)
                .orElse(null);
    }

    @Override
    public boolean checkPrincipalInClasse(Object ...args) {
        String teacherId = args[0].toString();
        int classeId = (int) args[1];
        return classeTeacherBossRepository.findTeacherIsBoss(UUID.fromString(teacherId), classeId).isPresent();
    }

    @Override
    public boolean checkPrincipal(UUID teacherId, UUID schoolId) {
        return classeTeacherBossRepository.findTeacherIsBoss(teacherId, schoolId).isPresent();
    }

    public boolean isTeacherInClasse(int classeId, UUID teacherId) {
        return classeTeacherBossRepository.findTeacherInClasse(classeId, teacherId).isPresent();
    }
}
