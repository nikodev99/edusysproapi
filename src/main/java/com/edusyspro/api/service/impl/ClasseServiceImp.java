package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.repository.GradeRepository;
import com.edusyspro.api.service.interfaces.ClasseServiceInterface;
import com.edusyspro.api.service.interfaces.ClasseStudentBossService;
import com.edusyspro.api.service.interfaces.ClasseTeacherBossService;
import com.edusyspro.api.service.interfaces.ScheduleService;
import com.edusyspro.api.service.mod.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ClasseServiceImp implements ClasseServiceInterface {
    private final ClasseRepository classeRepository;
    private final GradeRepository gradeRepository;
    private final ScheduleService scheduleService;
    private final ClasseTeacherBossService classeTeacherBossService;
    private final ClasseStudentBossService classeStudentBossService;
    private final TeacherService teacherService;

    @Override
    public ClasseDTO save(ClasseDTO entity) {
        boolean alreadyExists = classeAlreadyExists(entity);
        if(alreadyExists) {
            throw new AlreadyExistException("La classe " + entity.getName() + " existe déjà");
        }
        classeRepository.save(ClasseDTO.toEntity(entity));
        return entity;
    }

    @Override
    public List<ClasseDTO> saveAll(List<ClasseDTO> entities) {
        return List.of();
    }

    @Override
    public List<ClasseDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<ClasseDTO> fetchAll(String schoolId) {
        return classeRepository.findAllBasicValue(UUID.fromString(schoolId)).stream()
                .map(ClassBasicValue::toClasse)
                .toList();
    }

    @Override
    public Page<ClasseDTO> fetchAll(String schoolId, Pageable pageable) {
        return classeRepository.findAllCLassesBySchool(UUID.fromString(schoolId), pageable)
                .map(ClasseEssential::convertToDTO);
    }

    @Override
    public List<ClasseDTO> fetchAll(Object... args) {
        var schoolId = UUID.fromString(String.valueOf(args[0]));
        var classeName = "%" + args[1].toString() + "%";
        return classeRepository.findAllCLassesBySchool(schoolId, classeName).stream()
                .map(ClasseEssential::convertToDTO)
                .toList();
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
        ClasseDTO classe = classeRepository.findClasseById(id).convertToDTO();
        if (classe != null && classe.getId() > 0) {
            GradeDTO grade = classeRepository.findGradeClasseId(classe.getId()).convertToDTO();
            List<PlanningEssential> plannings = gradeRepository.findPlanningsByGrade(grade.getId(), UUID.fromString(schoolId));
            List<ScheduleDTO> schedules = scheduleService.getAllClasseSchedule(classe.getId(), grade.getSection());
            TeacherBossDTO teacherBoss = classeTeacherBossService.fetchTeacherBoss(classe.getId());
            StudentBossDTO studentBoss = classeStudentBossService.fetchStudentBoss(classe.getId());
            CourseDTO principalCourse = getClassePrincipalCourse(classe.getId());
            List<TeacherDTO> classeTeachers = teacherService.findAllClasseTeachers(classe.getId());

            grade.setPlanning(
                    plannings.stream()
                            .map(PlanningEssential::toDto)
                            .toList()
            );
            classe.setGrade(grade);
            classe.setSchedule(schedules);
            classe.setPrincipalTeacher(teacherBoss);
            classe.setPrincipalStudent(studentBoss);
            classe.setPrincipalCourse(principalCourse);
            classe.setClasseTeachers(classeTeachers);
        }
        return classe;
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

    private CourseDTO getClassePrincipalCourse(int classeId) {
        return classeRepository.findClassePrincipalCourse(classeId)
                .map(CourseEssential::toCourse)
                .orElse(null);
    }

    private boolean classeAlreadyExists(ClasseDTO entity) {
        return classeRepository.existsByName(
                entity.getName()
        );
    }
}
