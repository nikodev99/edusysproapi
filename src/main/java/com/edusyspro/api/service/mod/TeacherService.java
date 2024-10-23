package com.edusyspro.api.service.mod;

import com.edusyspro.api.dto.Teacher;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.service.impl.TeacherServiceImpl;
import com.edusyspro.api.service.interfaces.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService extends TeacherServiceImpl {
    public TeacherService(TeacherRepository teacherRepository, ScheduleService scheduleService) {
        super(teacherRepository, scheduleService);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return save(teacher);
    }

    public Page<Teacher> findAllTeachers(String schoolId, Pageable pageable) {
        return fetchAll(schoolId, pageable);
    }

    public List<Teacher> findAllTeachers(String schoolId, String lastName) {
        return fetchAll(schoolId, lastName);
    }

    public Teacher findTeacherById(String id, String schoolId) {
        return fetchOneById(UUID.fromString(id), schoolId);
    }
}