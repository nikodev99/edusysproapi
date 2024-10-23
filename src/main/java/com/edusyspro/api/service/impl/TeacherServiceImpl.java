package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.Teacher;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.service.interfaces.ScheduleService;
import com.edusyspro.api.service.interfaces.TeacherServiceInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public class TeacherServiceImpl implements TeacherServiceInterface {

    private final TeacherRepository teacherRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, ScheduleService scheduleService) {
        this.teacherRepository = teacherRepository;
        this.scheduleService = scheduleService;
    }

    @Override
    public Teacher save(Teacher entity) {
        if (teacherEmailExists(entity)) {
            throw new AlreadyExistException("L'adresse e-mail est déjà utilisée. Veuillez en fournir une autre.");
        }else {
            com.edusyspro.api.model.Teacher teacherEntity = Teacher.toEntity(entity);
            com.edusyspro.api.model.Teacher insertedTeacher = teacherRepository.save(teacherEntity);
            if (insertedTeacher.getId() != null) {
                entity = Teacher.fromEntity(insertedTeacher);
            }
        }
        return entity;
    }

    @Override
    public List<Teacher> saveAll(List<Teacher> entities) {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAll() {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<Teacher> fetchAll(String schoolId, Pageable pageable) {
        Page<com.edusyspro.api.model.Teacher> teacherEssentials = teacherRepository.findAllBySchoolId(UUID.fromString(schoolId), pageable);
        return teacherEssentials.map(Teacher::fromEntity);
    }

    @Override
    public List<Teacher> fetchAll(Object... args) {
        String schoolId = UUID.fromString(args[0].toString()).toString();
        String lastname = "%" + args[1].toString() + "%";
        List<com.edusyspro.api.model.Teacher> teacherEssentials = teacherRepository.findAllBySchoolId(UUID.fromString(schoolId), lastname);
        return teacherEssentials.stream()
                .map(Teacher::fromEntity)
                .toList();
    }

    @Override
    public Page<Teacher> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<Teacher> fetchAllById(UUID id) {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Teacher fetchOneById(UUID id) {
        return null;
    }

    @Override
    public Teacher fetchOneById(UUID id, String schoolId) {
        return Teacher.fromEntity(
                teacherRepository.findTeacherByIdAndSchoolId(id, UUID.fromString(schoolId))
                        .orElseThrow(() -> new NotFountException("Teacher was not found"))
        );
    }

    @Override
    public Teacher fetchOneById(UUID id, Object... args) {
        return null;
    }

    @Override
    public int update(Teacher entity) {
        return 0;
    }

    @Override
    public int patch(UUID id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(Teacher entity) {
        return 0;
    }

    private boolean teacherEmailExists(Teacher teacher) {
        return teacherRepository.existsByEmailIdAndSchoolId(teacher.getEmailId(), teacher.getSchool().getId());
    }
}
