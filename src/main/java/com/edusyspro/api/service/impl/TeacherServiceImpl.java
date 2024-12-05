package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.repository.context.UpdateContext;
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

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, ScheduleService scheduleService, UpdateContext updateContext) {
        this.teacherRepository = teacherRepository;
        this.scheduleService = scheduleService;
        this.updateContext = updateContext;
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
        List<com.edusyspro.api.model.Teacher> teacherEssentials = teacherRepository.findAllBySchoolId(UUID.fromString(schoolId), lastname);
        return teacherEssentials.stream()
                .map(TeacherDTO::fromEntity)
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
        dto.setAClasses(classes.stream().map(ClassBasicValue::toClasse).toList());
        dto.setCourses(courses.stream().map(CourseEssential::toCourse).toList());
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

    private boolean teacherEmailExists(TeacherDTO teacherDTO) {
        return teacherRepository.existsByPersonalInfoEmailIdAndSchoolId(teacherDTO.getPersonalInfo().getEmailId(), teacherDTO.getSchool().getId());
    }
}
