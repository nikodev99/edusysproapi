package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Course;
import com.edusyspro.api.entities.Department;
import com.edusyspro.api.entities.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void saveCourse() {
        School school = getSchool();
        Course course = Course.builder()
                .abbr("FRA")
                .course("Français")
                .department(getDepartment("DDL"))
                .school(school)
                .build();
        courseRepository.save(course);
    }

    @Test
    public void saveCourseS() {
        School school = getSchool();
        List<Course> courses = List.of(
                Course.builder()
                    .abbr("Maths")
                    .course("Mathématiques")
                    .department(getDepartment("DDS"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("SVT")
                    .course("Sciences de la vie et de la Terre")
                    .department(getDepartment("DDS"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("PC")
                    .course("Physique-Chimie")
                    .department(getDepartment("DDS"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("HG")
                    .course("Histoire-Géographie")
                    .department(getDepartment("DDL"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("Ang")
                    .course("Anglais")
                    .department(getDepartment("DDL"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("EPS")
                    .course("Éducation physique et sportive")
                    .department(getDepartment("DEP"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("Tech")
                    .course("Technologie")
                    .department(getDepartment("DDS"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("Dessin")
                    .course("Arts Plastiques")
                    .department(getDepartment("DAC"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("Musc")
                    .course("Musique")
                    .department(getDepartment("DAC"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("ECM")
                    .course("Éducation morale et civique")
                    .department(getDepartment("DAC"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("LAT")
                    .course("Latin")
                    .department(getDepartment("DDL"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("RUS")
                    .course("Russe")
                    .department(getDepartment("DDL"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("ESP")
                    .course("Espagnol")
                    .department(getDepartment("DDL"))
                    .school(school)
                    .build(),
                Course.builder()
                    .abbr("INF")
                    .course("Informatique")
                    .department(getDepartment("DDS"))
                    .school(school)
                    .build()
        );
        courseRepository.saveAll(courses);
    }

    @Test
    public void printCourses() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(c -> System.out.println("Course=" + c));
    }

    @Test
    public void printCourseByNameContaining() {
        Course course = courseRepository.getCourseByAbbrContainingIgnoreCase("Math");
        System.out.println(course);
    }

    private Department getDepartment(String code) {
        return departmentRepository.getDepartmentByCode(code);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("27a58e8a-a588-45dd-917e-6b690acd4b22"));
        return school.orElse(School.builder().build());
    }

}