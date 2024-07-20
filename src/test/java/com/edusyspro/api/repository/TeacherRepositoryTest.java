package com.edusyspro.api.repository;

import com.edusyspro.api.classes.ClasseRepository;
import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.*;
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
    public void saveTeachersOfPrimaire() {
        List<Teacher> teachers = Fake.getMultipleTeachers(
                6,
                MockUtils.SCHOOL_MOCK,
                new ClasseEntity[][]{
                        { getClasse(1)},
                        { getClasse(2)},
                        { getClasse(3)},
                        { getClasse(4)},
                        { getClasse(5)},
                        { getClasse(6)},
                },
                new Course[][]{}
        );
        teacherRepository.saveAll(teachers);
    }

    @Test
    public void saveTeachersOfCollege() {
        Course french = getCourse("FRA"); Course math = getCourse("Math");Course physique = getCourse("PC");
        Course histoire = getCourse("HG");Course anglais = getCourse("Ang");
        ClasseEntity sixieme = getClasse(7); ClasseEntity cinquieme = getClasse(8); ClasseEntity quatrieme = getClasse(9);
        ClasseEntity troisieme = getClasse(10); ClasseEntity seconde = getClasse(11);
        List<Teacher> teachers = Fake.getMultipleTeachers(
                16,
                MockUtils.SCHOOL_MOCK,
                new ClasseEntity[][]{
                        { sixieme, cinquieme },
                        { quatrieme, troisieme, seconde },
                        { sixieme, cinquieme, troisieme },
                        { quatrieme },
                        { sixieme, troisieme },
                        { cinquieme, quatrieme },
                        { sixieme, troisieme, getClasse(16), getClasse(17) },
                        { cinquieme, quatrieme },
                        { sixieme, cinquieme, troisieme, seconde, getClasse(12) },
                        { quatrieme, getClasse(13), getClasse(14), getClasse(15) },
                        { sixieme, cinquieme, quatrieme },
                        { troisieme, getClasse(14), getClasse(15) },
                        { sixieme, cinquieme, quatrieme },
                        { sixieme, cinquieme, quatrieme },
                        { sixieme, cinquieme, quatrieme },
                        { sixieme, cinquieme, quatrieme }
                },
                new Course[][]{
                        { french },
                        { french, getCourse("Philo") },
                        { math },
                        { math },
                        { physique },
                        { physique },
                        { histoire },
                        { histoire },
                        { anglais },
                        { anglais },
                        { getCourse("EPS") },
                        { getCourse("EPS") },
                        { getCourse("Dessin") },
                        { getCourse("Mus") },
                        { getCourse("ECM") },
                        { getCourse("Lat") }
                }
        );
        teacherRepository.saveAll(teachers);
    }

    @Test
    public void saveTeachers() {
        List<Teacher> teachers = Fake.getMultipleTeachers(
                7,
                MockUtils.SCHOOL_MOCK,
                new ClasseEntity[][]{
                        { getClasse(13) },
                        { getClasse(11) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                        { getClasse(11), getClasse(12), getClasse(13) },
                },
                new Course[][]{
                        { getCourse("FRA") },
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
        Teacher teacher = Fake.getTeacher(MockUtils.SCHOOL_MOCK, new ClasseEntity[] { getClasse(11) }, new Course[]{ getCourse("Maths") });
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

    private ClasseEntity getClasse(int id) {
        return classeRepository.getClasseById(id);
    }

    private Course getCourse(String abbr) {
        return courseRepository.getCourseByAbbrContainingIgnoreCase(abbr);
    }

}