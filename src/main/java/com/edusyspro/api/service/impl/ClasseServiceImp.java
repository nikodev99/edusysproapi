package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.model.enums.AffiliationStatus;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ClasseServiceImp implements ClasseServiceInterface {
    private final ClasseRepository classeRepository;
    private final PlanningService planningService;
    private final ScheduleService scheduleService;
    private final ClasseBossService<TeacherBossDTO> classeTeacherBossService;
    private final ClasseBossService<StudentBossDTO> classeStudentBossService;
    private final SchoolService schoolService;

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
        return classeRepository.findAllClassesBySchool(UUID.fromString(schoolId), pageable)
                .map(ClasseEssential::convertToDTO);
    }

    @Override
    public List<ClasseDTO> fetchAll(Object... args) {
        var schoolId = UUID.fromString(String.valueOf(args[0]));
        var classeName = "%" + args[1].toString() + "%";
        return classeRepository.findAllClassesBySchool(schoolId, classeName).stream()
                .map(ClasseEssential::convertToDTO)
                .toList();
    }

    @Override
    public Page<ClasseDTO> fetchAll(Pageable pageable, Object... args) {
        var teacherId = UUID.fromString(String.valueOf(args[0]));
        var schoolId = UUID.fromString(String.valueOf(args[1]));

        return classeRepository.findAllClasseTeacherContext(teacherId, AffiliationStatus.ACTIVE, schoolId, pageable)
                .map(ClasseEssential::convertToDTO);
    }

    @Override
    public List<ClasseDTO> fetchAllById(Integer id) {
        return List.of();
    }

    @Override
    public List<ClasseDTO> fetchAllById(Object... arg) {
        var teacherId = UUID.fromString(String.valueOf(arg[0]));
        var schoolId = UUID.fromString(String.valueOf(arg[1]));
        var classeName = "%" + arg[2].toString() + "%";

        return classeRepository.findAllClasseTeacherContext(teacherId, AffiliationStatus.ACTIVE, schoolId, classeName)
                .stream()
                .map(ClasseEssential::convertToDTO)
                .toList();
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
        return classeRepository.findClasseById(id).convertToDTO();
    }

    @Override
    public ClasseDTO fetchOneById(Integer id, String schoolId) {
        ClasseDTO classe = classeRepository.findClasseById(id).convertToDTO();
        UUID academicYear = UUID.fromString(schoolId);
        UUID realSchoolId = schoolService.getSchoolIdByAcademicYear(academicYear);
        if (classe != null && classe.getId() > 0) {
            GradeDTO grade = classeRepository.findGradeByClasseId(classe.getId()).convertToDTO();
            List<PlanningDTO> plannings = planningService.findBasicDynamicPlanningByGradeOfAMonth(grade.getId(), academicYear.toString());
            List<ScheduleDTO> schedules = scheduleService.getAllClasseSchedule(classe.getId(), grade.getSection(), academicYear.toString());
            TeacherBossDTO teacherBoss = classeTeacherBossService.fetchCurrentClasseBoss(classe.getId());
            StudentBossDTO studentBoss = classeStudentBossService.fetchCurrentClasseBoss(classe.getId());
            CourseDTO principalCourse = getClassePrincipalCourse(classe.getId());
            List<TeacherClasseDTO> classeTeachers = scheduleService.getClasseTeachers(
                    classe.getId(), realSchoolId.toString(), academicYear.toString()
            );

            grade.setPlanning(plannings);
            classe.setGrade(grade);
            classe.setSchedule(schedules);
            classe.setPrincipalTeacher(teacherBoss);
            classe.setPrincipalStudent(studentBoss);
            classe.setPrincipalCourse(principalCourse);
            classe.setClassTeacherCourses(classeTeachers);
        }
        return classe;
    }

    @Override
    public ClasseDTO fetchOneById(Integer id, Object... args) {
        return null;
    }

    @Override
    public ClasseDTO fetchOneByCustomColumn(String columnValue, String schoolId) {
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
    public Map<String, Boolean> update(ClasseDTO entity, Integer id) {
        if (!countClasseName(entity, Operator.GREATER_OR_EQUALS, 1)) {
            throw new AlreadyExistException("La classe " + entity.getName() + " existe déjà");
        }
        int hasUpdated = classeRepository.updateClasseValues(
                entity.getName(),
                entity.getCategory(),
                entity.getGrade().getId(),
                entity.getRoomNumber(),
                entity.getPrincipalCourse().getId(),
                entity.getMonthCost(),
                id
        ).orElseThrow();
        if (hasUpdated > 0) {
            return Map.of("updated", Boolean.TRUE);
        }else {
            return Map.of("updated", Boolean.FALSE);
        }
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return classeRepository.updateClassePrincipalCourse(id, (int) field.value()).orElseThrow();
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
                .map(CourseBasicValue::toCourse)
                .orElse(null);
    }

    private boolean classeAlreadyExists(ClasseDTO entity) {
        return countClasseName(entity, Operator.GREATER, 0);
    }

    private boolean countClasseName(ClasseDTO entity, Operator operator, int count) {
        Long countClasses = classeRepository.countBySchoolAndName(
                entity.getGrade().getId(),
                entity.getName()
        );

        return switch (operator) {
            case GREATER -> countClasses > count;
            case GREATER_OR_EQUALS -> countClasses >= count;
        };
    }
}
