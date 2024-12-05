package com.edusyspro.api.repository;

import com.edusyspro.api.model.CourseProgram;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class CourseProgramRepositoryTest {

    @Autowired
    private CourseProgramRepository courseProgramRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void saveCourseProgram() {
        Teacher teacher = getTeacher();
        List<CourseProgram> coursePrograms = List.of(
                CourseProgram.builder()
                        .topic("Les nombres entiers et décimaux")
                        .purpose("Représentation, comparaison, et ordre.")
                        .description("Addition, soustraction, multiplication, et division.")
                        .active(false)
                        .passed(true)
                        .updateDate(LocalDate.of(2024, 10, 10))
                        .semester(MockUtils.FIRST_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Les fractions")
                        .purpose("Introduction aux fractions (notion de part).")
                        .description("Comparaison et simplification des fractions. Opérations simples avec des fractions.")
                        .active(true)
                        .passed(false)
                        .semester(MockUtils.FIRST_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("La géométrie plane")
                        .purpose("Reconnaissance des figures géométriques (triangles, quadrilatères, cercles).")
                        .description("Construction avec règle et compas. Propriétés des angles.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.FIRST_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Les mesures")
                        .purpose("Conversion des unités (longueur, aire, volume, poids).")
                        .description("Calculs de périmètre, d'aire, et de volume.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.SECOND_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Les nombres relatifs")
                        .purpose("Introduction aux nombres positifs et négatifs.")
                        .description("Droite graduée et comparaison.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.SECOND_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Les bases de l'algèbre")
                        .purpose("Notion de variable et d'expression algébrique.")
                        .description("Résolution d'équations simples.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.SECOND_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Proportionnalité")
                        .purpose("Reconnaissance des situations proportionnelles.")
                        .description("Calculs de pourcentages et de produits en croix.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.SECOND_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Les statistiques et les probabilités")
                        .purpose("Lecture et interprétation de tableaux et graphiques.")
                        .description("Calculs de moyennes simples. Notion de probabilité.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.THIRD_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Symétries et transformations géométriques")
                        .purpose("Symétrie axiale.")
                        .description("Translations et rotations.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.THIRD_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build(),
                CourseProgram.builder()
                        .topic("Problèmes mathématiques appliqués")
                        .purpose("Résolution de problèmes en lien avec la vie quotidienne.")
                        .description("Raisonnement logique et méthodologie.")
                        .active(false)
                        .passed(false)
                        .semester(MockUtils.THIRD_SEMESTER)
                        .course(MockUtils.MATH)
                        .classe(MockUtils.SIX)
                        .teacher(teacher)
                        .build()
        );
        courseProgramRepository.saveAll(coursePrograms);
    }

    @Test
    public void testGettingTeachers() {
        Teacher teacher = getTeacher();
        System.out.printf("Teacher: %s%n", teacher);
    }

    private Teacher getTeacher() {
        return teacherRepository.findById(UUID.fromString("010792b9-c23b-4e88-be49-e18a9b7e4e22"))
                .orElseThrow();
    }

}
