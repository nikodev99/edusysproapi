package com.edusyspro.api.repository;

import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void saveNewCourse() {
        Course course = Course.builder()
                .abbr("Philo")
                .course("Philosophie")
                //.department(getDepartment("DDL"))
                .build();
        courseRepository.save(course);
    }

    @Test
    public void saveCourse() {
        Course course = Course.builder()
                .abbr("FRA")
                .course("Français")
                //.department(getDepartment("DDL"))
                .build();
        courseRepository.save(course);
    }

    @Test
    public void saveCourseS() {
        List<Course> courses = List.of(
                Course.builder()
                    .abbr("Maths")
                    .course("Mathématiques")
                    //.department(getDepartment("DDS"))
                    .build(),
                Course.builder()
                    .abbr("SVT")
                    .course("Sciences de la vie et de la Terre")
                    //.department(getDepartment("DDS"))
                    .build(),
                Course.builder()
                    .abbr("PC")
                    .course("Physique-Chimie")
                    //.department(getDepartment("DDS"))
                    .build(),
                Course.builder()
                    .abbr("HG")
                    .course("Histoire-Géographie")
                    //.department(getDepartment("DDL"))
                    .build(),
                Course.builder()
                    .abbr("Ang")
                    .course("Anglais")
                    //.department(getDepartment("DDL"))
                    .build(),
                Course.builder()
                    .abbr("EPS")
                    .course("Éducation physique et sportive")
                    //.department(getDepartment("DEP"))
                    .build(),
                Course.builder()
                    .abbr("Tech")
                    .course("Technologie")
                    //.department(getDepartment("DDS"))
                    .build(),
                Course.builder()
                    .abbr("Dessin")
                    .course("Arts Plastiques")
                    //.department(getDepartment("DAC"))
                    .build(),
                Course.builder()
                    .abbr("Musc")
                    .course("Musique")
                    //.department(getDepartment("DAC"))
                    .build(),
                Course.builder()
                    .abbr("ECM")
                    .course("Éducation morale et civique")
                    //.department(getDepartment("DAC"))
                    .build(),
                Course.builder()
                    .abbr("LAT")
                    .course("Latin")
                    //.department(getDepartment("DDL"))
                    .build(),
                Course.builder()
                    .abbr("RUS")
                    .course("Russe")
                    //.department(getDepartment("DDL"))
                    .build(),
                Course.builder()
                    .abbr("ESP")
                    .course("Espagnol")
                    //.department(getDepartment("DDL"))
                    .build(),
                Course.builder()
                    .abbr("INF")
                    .course("Informatique")
                    //.department(getDepartment("DDS"))
                    .build()
        );
        courseRepository.saveAll(courses);
    }

    @Test
    public void printCourses() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(c -> System.out.println("Course=" + c.getCourse()));
    }

    @Test
    public void printCourseByNameContaining() {
        Course course = courseRepository.getCourseByAbbrContainingIgnoreCase("Math");
        System.out.println(course);
    }

    /*
    private DepartmentDTO getDepartment(String code) {
        return departmentRepository.findDepartmentByCode(code).orElse(null);
    }*/


}