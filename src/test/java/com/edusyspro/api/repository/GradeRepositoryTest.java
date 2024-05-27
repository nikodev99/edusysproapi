package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Grade;
import com.edusyspro.api.entities.Planning;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.entities.enums.Section;
import com.edusyspro.api.service.AcademicYearService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private AcademicYearService academicYearService;

    @Autowired
    private SchoolRepository schoolRepository;


    @Test
    public void saveGradesAndPlanning() {
        Grade grade = Grade.builder()
                .section(Section.PRIMAIRE)
                .school(getSchool())
                .createdAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
                .build();
        grade.setPlanning(plannings(grade));
        gradeRepository.save(grade);
    }

    @Test
    public void saveAnotherGradesAndPlanning() {
        Grade grade = Grade.builder()
                .section(Section.COLLEGE)
                .school(getSchool())
                .createdAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
                .build();
        grade.setPlanning(plannings2(grade));
        gradeRepository.save(grade);
    }

    @Test
    public void saveThirdGradesAndPlanning() {
        Grade grade = Grade.builder()
                .section(Section.LYCEE)
                .school(getSchool())
                .createdAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
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
        Optional<School> school = schoolRepository.findById(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
        return school.orElse(School.builder().build());
    }

    private List<Planning> plannings(Grade grade) {
        Planning planning = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée du premier trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .build();

        Planning planning2 = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée du deuxième trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2024, 1, 5))
                .termEndDate(LocalDate.of(2024, 3, 26))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        Planning planning3 = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée du troisième trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2024, 4, 10))
                .termEndDate(LocalDate.of(2024, 6, 30))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        return List.of(planning, planning2, planning3);
    }

    private List<Planning> plannings2(Grade grade) {
        Planning planning = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée du premier trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        Planning planning1 = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée des devoirs départementaux du 1er trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 11, 15))
                .termEndDate(LocalDate.of(2023, 11, 26))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        Planning planning2 = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée du deuxième trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2024, 1, 5))
                .termEndDate(LocalDate.of(2024, 3, 26))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        Planning planning01 = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée des devoirs départementaux du 2e trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        Planning planning3 = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée du troisième trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2024, 4, 10))
                .termEndDate(LocalDate.of(2024, 6, 30))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        Planning planning02 = Planning.builder()
                .academicYear(getAcademicYear(getSchool()))
                .designation("Durée des devoirs départementaux du 3e trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .grade(grade)
                .school(getSchool())
                .build();

        return List.of(planning, planning1, planning2, planning01, planning3, planning02);
    }

    private String getAcademicYear(School school) {
        return academicYearService.getAcademicYearForSchool(school);
    }

}