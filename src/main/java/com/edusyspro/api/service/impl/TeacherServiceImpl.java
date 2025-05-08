package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.CustomMethod;
import com.edusyspro.api.service.interfaces.CourseProgramService;
import com.edusyspro.api.service.interfaces.ScheduleService;
import com.edusyspro.api.service.interfaces.TeacherServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class TeacherServiceImpl implements TeacherServiceInterface {

    private final TeacherRepository teacherRepository;
    private final ScheduleService scheduleService;
    private final UpdateContext updateContext;
    private final CourseProgramService courseProgramService;

    @Autowired
    public TeacherServiceImpl(
            TeacherRepository teacherRepository,
            ScheduleService scheduleService,
            UpdateContext updateContext,
            CourseProgramService courseProgramService
    ) {
        this.teacherRepository = teacherRepository;
        this.scheduleService = scheduleService;
        this.updateContext = updateContext;
        this.courseProgramService = courseProgramService;
    }

    @Override
    public TeacherDTO save(TeacherDTO entity) {
        if (teacherEmailExists(entity)) {
            throw new AlreadyExistException("L'adresse e-mail est déjà utilisée. Veuillez en fournir une autre.");
        }else {
            com.edusyspro.api.model.Teacher teacherEntity = TeacherDTO.toEntity(entity);
            com.edusyspro.api.model.Teacher insertedTeacher = teacherRepository.save(teacherEntity);
            if (insertedTeacher.getId() != null) {
                entity = TeacherDTO.fromEntity(insertedTeacher);
            }
        }
        return entity;
    }

    @Override
    public List<TeacherDTO> saveAll(List<TeacherDTO> entities) {
        return List.of();
    }

    @Override
    public List<TeacherDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<TeacherDTO> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<TeacherDTO> fetchAll(String schoolId, Pageable pageable) {
        Page<TeacherEssential> teacherEssentials = teacherRepository.findAllBySchoolId(UUID.fromString(schoolId), pageable);
        return teacherEssentials.map((t) -> {
            TeacherDTO teacherDTO = t.toTeacher();
            List<CourseBasicValue> courses = teacherRepository.findTeacherCourses(t.id(), UUID.fromString(schoolId));
            List<ClassBasicValue> classes = teacherRepository.findTeacherClasses(t.id(), UUID.fromString(schoolId));
            teacherDTO.setCourses(courses.stream().map(CourseBasicValue::toCourse).toList());
            teacherDTO.setAClasses(classes.stream().map(ClassBasicValue::toClasse).toList());
            return teacherDTO;
        });
    }

    @Override
    public List<TeacherDTO> fetchAll(Object... args) {
        String schoolId = UUID.fromString(args[0].toString()).toString();
        String lastname = "%" + args[1].toString() + "%";
        return teacherRepository.findAllBySchoolId(UUID.fromString(schoolId), lastname).stream()
                .map(TeacherEssential::toTeacher)
                .toList();
    }

    @Override
    public Page<TeacherDTO> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<TeacherDTO> fetchAllById(UUID id) {
        return List.of();
    }

    @Override
    public List<TeacherDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<TeacherDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<TeacherDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return teacherRepository.findAllClasseTeachers(Integer.parseInt(otherEntityId)).stream()
                .map(TeacherEssential::toTeacher)
                .toList();
    }

    @Override
    public List<TeacherDTO> fetchAllByOtherEntityId(Object... arg) {
        int classeId = Integer.parseInt(arg[0].toString());
        Section section = Section.valueOf(arg[1].toString());
        return teacherRepository.findAllTeacherBasicValue(classeId, section).stream()
                .map(TeacherBasic::toDTO)
                .toList();
    }

    @Override
    public TeacherDTO fetchOneById(UUID id) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneById(UUID id, String schoolId) {
        TeacherDTO dto = teacherRepository.findTeacherById(id, UUID.fromString(schoolId))
                        .orElseThrow(() -> new NotFountException("Teacher was not found"))
                        .toTeacher();
        List<ClassBasicValue> classes = teacherRepository.findTeacherClasses(dto.getId(), UUID.fromString(schoolId));
        List<CourseEssential> courses = teacherRepository.findTeacherEssentialCourses(dto.getId());
        List<CourseProgramDTO> programs = courseProgramService.fetchAllByOtherEntityId(String.valueOf(dto.getId()));
        dto.setAClasses(classes.stream().map(ClassBasicValue::toClasse).toList());
        dto.setCourses(courses.stream().map(CourseEssential::toCourse).toList());
        dto.setCourseProgram(programs);
        return dto;
    }

    @Override
    public TeacherDTO fetchOneById(UUID id, Object... args) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(TeacherDTO entity) {
        return 0;
    }

    @Override
    public Map<String, Boolean> update(TeacherDTO entity, UUID id) {
        return Map.of();
    }

    @Override
    public int patch(UUID id, UpdateField field) {
        return updateContext.updateTeacherField(field.field(), field.value(), id);
    }

    @Override
    public int delete(TeacherDTO entity) {
        return 0;
    }

    @Override
    public Map<String, Long> count(UUID id) {
        return Map.of("count", teacherRepository.countTeacherStudents(id));
    }

    @Override
    public Map<String, Long> count(String schoolId) {
       return Map.of();
    }

    @Override
    public Map<String, Long> count(Object... args) {
        return Map.of();
    }

    @Override
    public List<Map<String, Object>> countStudentsByClasse(UUID teacherId) {
        List<Object[]> counts = teacherRepository.countAllTeacherStudentsByClasses(teacherId);
        return counts.stream()
                .map(row -> Map.of(
                        "classe", row[0],
                        "count", row[1]
                ))
                .toList();
    }

    @Override
    public GenderCount countAllTeachers(String schoolId) {
        List<Object[]> countTeachers = teacherRepository.countAllTeachers(UUID.fromString(schoolId));
        return CustomMethod.genderCountInClasse(countTeachers);
    }

    private boolean teacherEmailExists(TeacherDTO teacherDTO) {
        return teacherRepository.existsByPersonalInfoEmailIdAndSchoolId(teacherDTO.getPersonalInfo().getEmailId(), teacherDTO.getSchool().getId());
    }
}
