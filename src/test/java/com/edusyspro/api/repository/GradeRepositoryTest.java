package com.edusyspro.api.repository;

import com.edusyspro.api.school.entities.AcademicYear;
import com.edusyspro.api.school.entities.Grade;
import com.edusyspro.api.school.entities.Planning;
import com.edusyspro.api.school.entities.School;
import com.edusyspro.api.entities.enums.Section;
import com.edusyspro.api.school.repos.AcademicYearRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GradeRepositoryTest {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Autowired
    private SchoolRepository schoolRepository;


    @Test
    public void saveGradesAndPlanning() {
        Grade grade = Grade.builder()
                .section(Section.PRIMAIRE)
                .school(getSchool())
                .build();
        grade.setPlanning(plannings(grade));
        gradeRepository.save(grade);
    }

    @Test
    public void saveAnotherGradesAndPlanning() {
        Grade grade = Grade.builder()
                .section(Section.COLLEGE)
                .school(getSchool())
                .build();
        grade.setPlanning(plannings2(grade));
        gradeRepository.save(grade);
    }

    @Test
    public void saveThirdGradesAndPlanning() {
        Grade grade = Grade.builder()
                .section(Section.LYCEE)
                .school(getSchool())
                .build();
        grade.setPlanning(plannings2(grade));
        gradeRepository.save(grade);
    }


    @Test
    public void retrieveGrades() {
        List<Grade> grades = gradeRepository.findAll();
        grades.forEach(g -> System.out.println("Grades=" + g));
    }

    @Test
    public void retrieveAGrade() {
        Optional<Grade> grade = gradeRepository.findById(3);
        System.out.println("Grade=" + grade.orElseThrow());
    }

    @Test
    public void updateSubSection() {
        int updatedRow = gradeRepository.updateGradeSubSectionById("Serie A", 3);
        assertEquals(1, updatedRow);
        assertTrue(updatedRow != 0);
    }

    @Test
    public void printGradesWithItsPlannings() {
        List<Grade> grades = gradeRepository.findAllBySectionName(Section.LYCEE);
        System.out.println(grades);
    }

    @Test
    public void printGradesBySection() {
        Grade grades = gradeRepository.getGradeBySection(Section.LYCEE);
        System.out.println(grades);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("e4525e5a-2c64-44c4-b40b-82aeeebef2ce"));
        return school.orElse(School.builder().build());
    }

    private List<Planning> plannings(Grade grade) {
        Planning planning = Planning.builder()
                .academicYear(academicYear())
                .designation("Durée du premier trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .build();

        Planning planning2 = Planning.builder()
                .academicYear(academicYear())
                .designation("Durée du deuxième trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2024, 1, 5))
                .termEndDate(LocalDate.of(2024, 3, 26))
                .grade(grade)
                .build();

        Planning planning3 = Planning.builder()
                .academicYear(academicYear())
                .designation("Durée du troisième trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2024, 4, 10))
                .termEndDate(LocalDate.of(2024, 6, 30))
                .grade(grade)
                .build();

        return List.of(planning, planning2, planning3);
    }

    private List<Planning> plannings2(Grade grade) {
        Planning planning = Planning.builder()
                .designation("Durée du premier trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .academicYear(academicYear())
                .build();

        Planning planning1 = Planning.builder()
                .designation("Durée des devoirs départementaux du 1er trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 11, 15))
                .termEndDate(LocalDate.of(2023, 11, 26))
                .grade(grade)
                .academicYear(academicYear())
                .build();

        Planning planning2 = Planning.builder()
                .designation("Durée du deuxième trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2024, 1, 5))
                .termEndDate(LocalDate.of(2024, 3, 26))
                .grade(grade)
                .academicYear(academicYear())
                .build();

        Planning planning01 = Planning.builder()
                .designation("Durée des devoirs départementaux du 2e trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .academicYear(academicYear())
                .build();

        Planning planning3 = Planning.builder()
                .designation("Durée du troisième trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2024, 4, 10))
                .termEndDate(LocalDate.of(2024, 6, 30))
                .grade(grade)
                .academicYear(academicYear())
                .build();

        Planning planning02 = Planning.builder()
                .designation("Durée des devoirs départementaux du 3e trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .academicYear(academicYear())
                .build();

        return List.of(planning, planning1, planning2, planning01, planning3, planning02);
    }

    private AcademicYear academicYear() {
        return academicYearRepository.findAcademicYearBySchoolIdAndCurrentIsTrue(UUID.fromString("74db8563-3753-41d7-8106-153a7d71dd2e"));
    }

}