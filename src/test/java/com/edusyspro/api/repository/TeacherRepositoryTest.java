package com.edusyspro.api.repository;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.TeacherClassCourse;
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
    public void testSaveTwoTeachers() {
        Course french = getCourse("FRA"); Course math = getCourse("Math");Course physique = getCourse("PC");
        Course histoire = getCourse("HG");Course anglais = getCourse("Ang");
        ClasseEntity sixieme = getClasse(7); ClasseEntity cinquieme = getClasse(8); ClasseEntity quatrieme = getClasse(9);
        ClasseEntity troisieme = getClasse(10); ClasseEntity seconde = getClasse(11);
        List<Teacher> teachers = Fake.getMultipleTeachers(
                2,
                MockUtils.SCHOOL_MOCK,
                new TeacherClassCourse[][]{
                        new TeacherClassCourse[]{
                                TeacherClassCourse.builder()
                                        .course(math)
                                        .classe(quatrieme)
                                        .build(),
                                TeacherClassCourse.builder()
                                        .course(physique)
                                        .classe(troisieme)
                                        .build()
                        },
                        new TeacherClassCourse[]{
                                TeacherClassCourse.builder()
                                        .course(physique)
                                        .classe(quatrieme)
                                        .build()
                        }
                }
        );
        teacherRepository.saveAll(teachers);
    }

    @Test
    public void testGetTeacherById() {
        List<TeacherClassCourse> teacher =  teacherRepository.findTeacherByClassesAndCourses(UUID.fromString("143ecd9f-e67d-47bc-8b90-4583b4d505cc"));
        System.out.println("Teacher: " + teacher);
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