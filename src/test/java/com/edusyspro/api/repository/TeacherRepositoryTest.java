package com.edusyspro.api.repository;

import com.edusyspro.api.classes.ClassRepository;
import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.*;
import com.edusyspro.api.utils.Fake;
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
    private SchoolRepository schoolRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void saveTeachers() {
        List<Teacher> teachers = Fake.getMultipleTeachers(
                7,
                getSchool(),
                new ClasseEntity[][]{
                        { getClasse(11), getClasse(13) },
                        { getClasse(11) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                },
                new Course[][]{
                        { getCourse("PC") },
                        { getCourse("Ang") },
                        { getCourse("EPS") },
                        { getCourse("Dessin") },
                        { getCourse("Musc") },
                        { getCourse("ECM") },
                        { getCourse("Lat") },
                }
        );
        teacherRepository.saveAll(teachers);
    }

    @Test
    public void saveMathTeacher() {
        Teacher teacher = Fake.getTeacher(getSchool(), new ClasseEntity[] { getClasse(11) }, new Course[]{ getCourse("Maths") });
        teacherRepository.save(teacher);
    }

    @Test
    public void getTeacher() {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(UUID.fromString("621620ee-ec4b-406b-afa5-2497b63d4b1a"));
        Teacher teacher = optionalTeacher.orElseThrow();
        System.out.println("Prof de CP1=" + teacher);
    }

    @Test
    public void getTeacherByClasseName() {
        Teacher teacher = teacherRepository.getTeacherByClassesName("CE1");
        System.out.println(teacher);
    }

    @Test
    public void getTeacherByClasseNameAndCourseId() {
        Teacher teacher = teacherRepository.getTeacherByClassesNameAndCourseId("6e", 2);
        System.out.println(teacher);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
        return school.orElseThrow();
    }

    private Department getDepartment(String code) {
        return departmentRepository.getDepartmentByCode(code);
    }

    private ClasseEntity getClasse(int id) {
        return classRepository.getClasseById(id);
    }

    private Course getCourse(String abbr) {
        return courseRepository.getCourseByAbbrContainingIgnoreCase(abbr);
    }

}