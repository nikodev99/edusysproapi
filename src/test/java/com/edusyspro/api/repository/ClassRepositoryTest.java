package com.edusyspro.api.repository;

import com.edusyspro.api.classes.ClassRepository;
import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.*;
import com.edusyspro.api.entities.enums.Section;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class ClassRepositoryTest {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void savePrimaryClasses() {
        List<ClasseEntity> classeEntityList = List.of(
                ClasseEntity.builder()
                        .name("CP1")
                        .category("Cours Préparatoire 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("CP2")
                        .category("Cours Préparatoire 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("CE1")
                        .category("Cours Élémentaire 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("CE2")
                        .category("Cours Élémentaire 2")
                        .grade(getGrade(Section.PRIMAIRE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("CM1")
                        .category("Cours Moyen 1")
                        .grade(getGrade(Section.PRIMAIRE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("CM2")
                        .category("Cours Moyen 2")
                        .grade(getGrade(Section.PRIMAIRE))
                        .school(getSchool())
                        .build()
        );
        classRepository.saveAll(classeEntityList);
    }

    @Test
    public void saveCollegeClasses() {
        List<ClasseEntity> classeEntityList = List.of(
                ClasseEntity.builder()
                        .name("6e")
                        .category("Sixième")
                        .grade(getGrade(Section.COLLEGE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("5e")
                        .category("Cinquième")
                        .grade(getGrade(Section.COLLEGE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("4e")
                        .category("Quatrième")
                        .grade(getGrade(Section.COLLEGE))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("3e")
                        .category("Troisième")
                        .grade(getGrade(Section.COLLEGE))
                        .school(getSchool())
                        .build()
        );
        classRepository.saveAll(classeEntityList);

    }

    @Test
    public void saveLyceeClasses() {
        List<ClasseEntity> classeEntityList = List.of(
                ClasseEntity.builder()
                        .name("2nde A")
                        .category("Seconde Littéraire")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(2))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("STC")
                        .category("Seconde Scientifique")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(3))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("1er A")
                        .category("Première Littéraire")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(2))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("1er S")
                        .category("Première Scientifique")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(3))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("Terminale A")
                        .category("Terminale Littéraire")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(2))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("Terminale D")
                        .category("Terminale Biological")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(4))
                        .school(getSchool())
                        .build(),
                ClasseEntity.builder()
                        .name("Terminale C")
                        .category("Quatrième")
                        .grade(getGrade(Section.LYCEE))
                        .principalCourse(getPrincipalCourse(3))
                        .school(getSchool())
                        .build()
        );
        classRepository.saveAll(classeEntityList);
    }

    @Test
    public void printAllClasses() {
        List<ClasseEntity> classeEntityList = classRepository.findAll();
        classeEntityList.forEach(c -> System.out.println("Classe=" + c));
    }

    @Test
    public void printAllTeachersOfAClasse() {
        ClasseEntity classeEntity = classRepository.getClasseById(11);
        classeEntity.getTeachers().forEach(t -> System.out.println("Name=" + t.getFirstName() + "\nClasse=" + t.getAClasses() + "\nCours=" + t.getCourses() +"\n------------------"));
    }

    @Test
    public void printAllTeachersOfClasse() {
        ClasseEntity classeEntity = classRepository.getClasseById(12);
        List<Teacher> teachers = classeEntity.getTeachers();
        List<List<Course>> courseLits = teachers.stream().map(Teacher::getCourses).toList();
        courseLits.forEach(c -> System.out.println("Courses=" + c + "\n-------------------------"));
    }

    @Test
    public void printTeacherOfPrimary() {
        List<ClasseEntity> classeEntityList = classRepository.getClassesByGradeSection(Section.PRIMAIRE);
        List<List<Teacher>> teachers = classeEntityList.stream().map(ClasseEntity::getTeachers).toList();
        teachers.forEach(System.out::println);
    }

    @Test
    public void printTeacherOfSecondaryClass() {
        ClasseEntity classeEntity = classRepository.getClasseById(11);
        List<Teacher> teachers = classeEntity.getTeachers();
        teachers.forEach(t -> System.out.println("Proj de 6e=" + t));
    }

    @Test
    public void printClassSchedule() {
        ClasseEntity classeEntity = classRepository.getClasseById(7);
        List<Schedule> schedules = classeEntity.getSchedule();
        schedules.forEach(System.out::println);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("27a58e8a-a588-45dd-917e-6b690acd4b22"));
        return school.orElseThrow();
    }

    private Course getPrincipalCourse(int id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.orElseThrow();
    }

    private Grade getGrade(Section section) {
        return gradeRepository.getGradeBySection(section);
    }

}