package com.edusyspro.api.repository;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.dto.custom.ClassBasicValue;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.Schedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ClasseDTORepositoryTest {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void savePrimaryClasses() {
        List<ClasseEntity> classeEntityList = List.of(
                ClasseEntity.builder()
                        .name("CP1")
                        .category("Cours Préparatoire 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .build(),
                ClasseEntity.builder()
                        .name("CP2")
                        .category("Cours Préparatoire 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .build(),
                ClasseEntity.builder()
                        .name("CE1")
                        .category("Cours Élémentaire 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .build(),
                ClasseEntity.builder()
                        .name("CE2")
                        .category("Cours Élémentaire 2")
                        .grade(getGrade(Section.PRIMAIRE))
                        .build(),
                ClasseEntity.builder()
                        .name("CM1")
                        .category("Cours Moyen 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .build(),
                ClasseEntity.builder()
                        .name("CM2")
                        .category("Cours Moyen 2")
                        .grade(getGrade(Section.PRIMAIRE))
                        .build()
        );
        classeRepository.saveAll(classeEntityList);
    }

    @Test
    public void saveCollegeClasses() {
        List<ClasseEntity> classeEntityList = List.of(
                ClasseEntity.builder()
                        .name("6e")
                        .category("Sixième")
                        .grade(getGrade(Section.COLLEGE))
                        .build(),
                ClasseEntity.builder()
                        .name("5e")
                        .category("Cinquième")
                        .grade(getGrade(Section.COLLEGE))
                        .build(),
                ClasseEntity.builder()
                        .name("4e")
                        .category("Quatrième")
                        .grade(getGrade(Section.COLLEGE))
                        .build(),
                ClasseEntity.builder()
                        .name("3e")
                        .category("Troisième")
                        .grade(getGrade(Section.COLLEGE))
                        .build()
        );
        classeRepository.saveAll(classeEntityList);

    }

    @Test
    public void saveLyceeClasses() {
        List<ClasseEntity> classeEntityList = List.of(
                ClasseEntity.builder()
                        .name("2nde A")
                        .category("Seconde Littéraire")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(2))
                        .build(),
                ClasseEntity.builder()
                        .name("STC")
                        .category("Seconde Scientifique")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(3))
                        .build(),
                ClasseEntity.builder()
                        .name("1er A")
                        .category("Première Littéraire")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(2))
                        .build(),
                ClasseEntity.builder()
                        .name("1er S")
                        .category("Première Scientifique")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(3))
                        .build(),
                ClasseEntity.builder()
                        .name("Terminale A")
                        .category("Terminale Littéraire")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(2))
                        .build(),
                ClasseEntity.builder()
                        .name("Terminale D")
                        .category("Terminale Biological")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(4))
                        .build(),
                ClasseEntity.builder()
                        .name("Terminale C")
                        .category("Terminal Scientifique")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(3))
                        .build()
        );
        classeRepository.saveAll(classeEntityList);
    }

    @Test
    public void printAllClasses() {
        List<ClasseEntity> classeEntityList = classeRepository.findAll();
        classeEntityList.forEach(c -> System.out.println("ClasseDTO=" + c.getName()));
    }

    @Test
    public void printClassSchedule() {
        ClasseEntity classeEntity = classeRepository.getClasseById(7);
        List<Schedule> schedules = classeEntity.getSchedule();
        schedules.forEach(System.out::println);
    }

    @Test
    public void printBasicClassValues() {
        List<ClassBasicValue> classBasicValues = classeRepository.findAllBasicValue(UUID.fromString(ConstantUtils.SCHOOL_ID));
        System.out.println("BasicClassValues= " + classBasicValues);
    }

    @Test
    public void classeExistsTest() {
        ClasseEntity classe = ClasseEntity.builder()
                .name("Terminale A")
                .category("Terminale Littéraire")
                .grade(getGrade(Section.LYCEE))
                .monthCost(new BigDecimal("3300"))
                .build();
        boolean exists = classeRepository.existsByName(classe.getName());
        assertTrue(exists);
    }

    @Test
    public void TestExistWhenUpdating() {
        int e = classeRepository.countByName("STC", 12);
        System.out.println("COUNT: " + e);
    }

    private Course getPrincipalCourse(int id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.orElseThrow();
    }

    private Grade getGrade(Section section) {
        return gradeRepository.getGradeBySection(section);
    }

}