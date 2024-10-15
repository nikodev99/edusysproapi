package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.Student;
import com.edusyspro.api.dto.Teacher;
import com.edusyspro.api.dto.TeacherEssential;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.AlreadyExistException;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.TeacherClassCourse;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.service.interfaces.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        com.edusyspro.api.model.Teacher teacherEntity = com.edusyspro.api.model.Teacher.builder().build();
        BeanUtils.copyProperties(entity, teacherEntity);
        if (teacherEmailExists(entity)) {
            throw new AlreadyExistException("L'adresse e-mail est déjà utilisée. Veuillez en fournir une autre.");
        }else if (teacherAlreadyInClasseAndHasCourse(entity)) {
            throw new AlreadyExistException("Un enseignant avec le même cours dans la même classe détecté.");
        }else {
            com.edusyspro.api.model.Teacher insertedTeacher = teacherRepository.save(teacherEntity);
            if (insertedTeacher.getId() != null) {
                BeanUtils.copyProperties(insertedTeacher, entity);
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
        Page<com.edusyspro.api.model.Teacher> teacherEssentials = teacherRepository.findAllTeachers(UUID.fromString(schoolId), pageable);
        return teacherEssentials.map(Teacher::fromEntity);
    }

    @Override
    public List<Teacher> fetchAll(Object... args) {
        String schoolId = UUID.fromString(args[0].toString()).toString();
        String lastname = "%" + args[1].toString() + "%";
        List<com.edusyspro.api.model.Teacher> teacherEssentials = teacherRepository.findAllTeachers(UUID.fromString(schoolId), lastname);
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

    private boolean teacherAlreadyInClasseAndHasCourse(Teacher teacher) {
        List<TeacherClassCourse> teacherClassCourses = teacher.getTeacherClassCourses();
        List<TeacherClassCourse> existing = new ArrayList<>();
        for (TeacherClassCourse teacherClassCourse : teacherClassCourses) {
            Course course = teacherClassCourse.getCourse();
            ClasseEntity classe = teacherClassCourse.getClasse();
            List<TeacherClassCourse> list;
            if (course != null) {
                list = teacherRepository.findTeacherByCourseAndClasse(
                        classe.getId(), course.getId(), teacher.getSchool().getId()
                );
            }else {
                list = teacherRepository.findTeacherByClasse(
                        classe.getId(), teacher.getSchool().getId()
                );
            }
            existing.addAll(list);
        }
        return !existing.isEmpty();
    }

    private boolean teacherEmailExists(Teacher teacher) {
        return teacherRepository.existsByEmailIdAndSchoolId(teacher.getEmailId(), teacher.getSchool().getId());
    }
}
