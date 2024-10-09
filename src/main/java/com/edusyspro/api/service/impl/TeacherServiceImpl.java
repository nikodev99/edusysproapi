package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.Teacher;
import com.edusyspro.api.dto.TeacherEssential;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher save(Teacher entity) {
        return null;
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
        Page<TeacherEssential> teacherEssentials = teacherRepository.findAllTeachers(UUID.fromString(schoolId), pageable);
        return teacherEssentials.map(teacherEssential -> {
            Teacher teacher = teacherEssential.toTeacher();
            List<Course> courses = teacherRepository.findTeacherCourses(teacher.getId(), UUID.fromString(schoolId));
            if(courses == null || courses.isEmpty()) {
                teacher.setCourses(Collections.emptyList());
            }else {
                teacher.setCourses(courses);
            }
            return teacher;
        });
    }

    @Override
    public List<Teacher> fetchAll(Object... args) {
        return List.of();
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
}
