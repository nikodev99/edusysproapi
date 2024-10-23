package com.edusyspro.api.repository;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void getTeacher() {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(UUID.fromString("621620ee-ec4b-406b-afa5-2497b63d4b1a"));
        Teacher teacher = optionalTeacher.orElseThrow();
        System.out.println("Prof de CP1=" + teacher);
    }

    @Test
    public void getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        System.out.println(teachers);
    }

    private ClasseEntity getClasse(int id) {
        return classeRepository.getClasseById(id);
    }

    private Course getCourse(String abbr) {
        return courseRepository.getCourseByAbbrContainingIgnoreCase(abbr);
    }

}