package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.Teacher;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.service.interfaces.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> fetchAll() {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAll(int page) {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAll(int page, int pageSize) {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAll(int page, int pageSize, String sort) {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public List<Teacher> fetchAllById(int page, int pageSize, UUID id) {
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
    public int update(Teacher entity) {
        return 0;
    }

    @Override
    public int delete(Teacher entity) {
        return 0;
    }
}
