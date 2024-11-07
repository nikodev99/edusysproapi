package com.edusyspro.api.repository;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.utils.Fake;
import com.edusyspro.api.utils.MockUtils;
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
    public void saveTeachersOfPrimary() {
        teacherRepository.saveAll(Fake.getMultipleTeachers(6, MockUtils.SCHOOL_MOCK, new ClasseEntity[][]{
                {MockUtils.CP1}, {MockUtils.CP2}, {MockUtils.CE1}, {MockUtils.CE2}, {MockUtils.CM1}, {MockUtils.CM2}
        }, new Course[][]{}));
    }

    @Test
    public void saveTeachersOfSecondary() {
        teacherRepository.saveAll(Fake.getMultipleTeachers(20, MockUtils.SCHOOL_MOCK, new ClasseEntity[][]{
                {MockUtils.SIX, MockUtils.FOUR},
                {MockUtils.FIVE, MockUtils.THREE, MockUtils.SECONDA, MockUtils.TERC},
                {MockUtils.SIX, MockUtils.FIVE},
                {MockUtils.FOUR, MockUtils.THREE},
                {MockUtils.SIX, MockUtils.FIVE},
                {MockUtils.FOUR},
                {MockUtils.THREE},
                {MockUtils.SIX, MockUtils.FOUR},
                {MockUtils.FIVE, MockUtils.THREE},
                {MockUtils.SIX, MockUtils.FIVE, MockUtils.FOUR},
                {MockUtils.THREE},
                {MockUtils.SIX},
                {MockUtils.FIVE, MockUtils.FOUR},
                {MockUtils.THREE},
                {MockUtils.SIX, MockUtils.FIVE, MockUtils.FOUR},
                {MockUtils.THREE},
                {MockUtils.SIX, MockUtils.FIVE, MockUtils.FOUR},
                {MockUtils.SIX, MockUtils.FIVE, MockUtils.FOUR},
                {MockUtils.SIX, MockUtils.FIVE, MockUtils.FOUR},
                {MockUtils.SIX, MockUtils.FIVE, MockUtils.FOUR},
        }, new Course[][]{
                {MockUtils.FRA},
                {MockUtils.FRA, MockUtils.PHILO},
                {MockUtils.ANG},
                {MockUtils.ANG},
                {MockUtils.MATH},
                {MockUtils.MATH},
                {MockUtils.MATH},
                {MockUtils.PC},
                {MockUtils.PC},
                {MockUtils.SVT},
                {MockUtils.SVT},
                {MockUtils.HG},
                {MockUtils.HG},
                {MockUtils.HG},
                {MockUtils.EPS},
                {MockUtils.EPS},
                {MockUtils.DES},
                {MockUtils.MUSC},
                {MockUtils.ECM},
                {MockUtils.LAT},
        }));
    }

    @Test
    public void saveTeachersOfHighSchool() {
        teacherRepository.saveAll(Fake.getMultipleTeachers(22, MockUtils.SCHOOL_MOCK, new ClasseEntity[][]{
                {MockUtils.STC, MockUtils.FIRSTA, MockUtils.FIRSTS},
                {MockUtils.TERA, MockUtils.TERD},
                {MockUtils.SECONDA, MockUtils.FIRSTA, MockUtils.TERA},
                {MockUtils.STC},
                {MockUtils.FIRSTS, MockUtils.TERD},
                {MockUtils.TERC},
                {MockUtils.SECONDA, MockUtils.STC},
                {MockUtils.FIRSTA, MockUtils.TERA},
                {MockUtils.FIRSTS, MockUtils.TERD},
                {MockUtils.TERC},
                {MockUtils.SECONDA, MockUtils.TERA},
                {MockUtils.STC, MockUtils.FIRSTA, MockUtils.FIRSTS, MockUtils.TERD, MockUtils.TERC},
                {MockUtils.SECONDA, MockUtils.STC, MockUtils.TERD},
                {MockUtils.FIRSTA, MockUtils.FIRSTS, MockUtils.TERA, MockUtils.TERC},
                {MockUtils.SECONDA, MockUtils.STC, MockUtils.FIRSTA, MockUtils.TERA},
                {MockUtils.SECONDA, MockUtils.FIRSTA, MockUtils.TERA, MockUtils.TERD},
                {MockUtils.STC, MockUtils.FIRSTS, MockUtils.TERC},
                {MockUtils.STC, MockUtils.TERC},
                {MockUtils.FIRSTS, MockUtils.TERD},
                {MockUtils.STC, MockUtils.TERC},
                {MockUtils.FIRSTS, MockUtils.TERD},
                {MockUtils.TERA},
        }, new Course[][]{
                {MockUtils.PHILO},
                {MockUtils.PHILO},
                {MockUtils.MATH},
                {MockUtils.MATH},
                {MockUtils.MATH},
                {MockUtils.MATH},
                {MockUtils.HG},
                {MockUtils.HG},
                {MockUtils.HG},
                {MockUtils.HG},
                {MockUtils.ANG},
                {MockUtils.ANG},
                {MockUtils.EPS},
                {MockUtils.EPS},
                {MockUtils.ESP},
                {MockUtils.FRA},
                {MockUtils.FRA},
                {MockUtils.SVT},
                {MockUtils.SVT},
                {MockUtils.PC},
                {MockUtils.PC},
                {MockUtils.RUS},
        }));
    }

    @Test
    public void printTeacherByClasse() {
        Teacher teacher = teacherRepository.findTeacherByClasseId(5).orElse(null);
        System.out.println(teacher);
    }

    @Test
    public void printTeacherByClasseAndCourseId() {
        Teacher teacher = teacherRepository.findTeacherByClasseIdAndCourseId(17, 1).orElse(null);
        System.out.println(teacher);
    }

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