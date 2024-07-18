package com.edusyspro.api.repository;

import com.edusyspro.api.school.entities.Grade;
import com.edusyspro.api.school.entities.Planning;
import com.edusyspro.api.school.entities.School;
import com.edusyspro.api.entities.enums.Section;
import com.edusyspro.api.school.services.AcademicYearService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class PlanningRepositoryTest {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private AcademicYearService academicYearService;

    @Autowired
    private GradeRepository gradeRepository;

    @Test
    public void savePlanning() {
        School school = getSchool();
        Grade grade = Grade.builder()
                .section(Section.LYCEE)
                .subSection("Serie D")
                .school(school)
                .build();
        Planning planning = Planning.builder()
                .designation("Durée du premier trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .build();
        planningRepository.save(planning);
    }

    @Test
    public void savePlanning2() {
        School school = getSchool();
        Grade grade = getGrade();

        Planning planning1 = Planning.builder()
                .designation("Durée des devoirs départementaux du 1er trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 11, 15))
                .termEndDate(LocalDate.of(2023, 11, 26))
                .grade(grade)
                .build();

        Planning planning2 = Planning.builder()
                .designation("Durée du deuxième trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2024, 1, 5))
                .termEndDate(LocalDate.of(2024, 3, 26))
                .grade(grade)
                .build();

        Planning planning01 = Planning.builder()
                .designation("Durée des devoirs départementaux du 2e trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .build();

        Planning planning3 = Planning.builder()
                .designation("Durée du troisième trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2024, 4, 10))
                .termEndDate(LocalDate.of(2024, 6, 30))
                .grade(grade)
                .build();

        Planning planning02 = Planning.builder()
                .designation("Durée des devoirs départementaux du 3e trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .build();
        List<Planning> plannings = List.of(planning1, planning2, planning01, planning3, planning02);
        planningRepository.saveAll(plannings);
    }

    @Test
    public void savePlanning3() {
        School school = getSchool();

        Grade grade = Grade.builder()
                .section(Section.LYCEE)
                .subSection("Serie C")
                .school(school)
                .build();

        Planning planning = Planning.builder()
                .designation("Durée du premier trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .build();

        Planning planning1 = Planning.builder()
                .designation("Durée des devoirs départementaux du 1er trimestre")
                .semestre("1er Trimestre")
                .termStartDate(LocalDate.of(2023, 11, 15))
                .termEndDate(LocalDate.of(2023, 11, 26))
                .grade(grade)
                .build();

        Planning planning2 = Planning.builder()
                .designation("Durée du deuxième trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2024, 1, 5))
                .termEndDate(LocalDate.of(2024, 3, 26))
                .grade(grade)
                .build();

        Planning planning01 = Planning.builder()
                .designation("Durée des devoirs départementaux du 2e trimestre")
                .semestre("2e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .build();

        Planning planning3 = Planning.builder()
                .designation("Durée du troisième trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2024, 4, 10))
                .termEndDate(LocalDate.of(2024, 6, 30))
                .grade(grade)
                .build();

        Planning planning02 = Planning.builder()
                .designation("Durée des devoirs départementaux du 3e trimestre")
                .semestre("3e Trimestre")
                .termStartDate(LocalDate.of(2023, 10, 1))
                .termEndDate(LocalDate.of(2023, 12, 16))
                .grade(grade)
                .build();
        List<Planning> plannings = List.of(planning, planning1, planning2, planning01, planning3, planning02);
        planningRepository.saveAll(plannings);
    }

    @Test
    public void printPlannings() {
        List<Planning> plannings = planningRepository.findAll();
        plannings.forEach(p -> System.out.println("Planning=" + p));
    }

    @Test
    public void updateAPlanning() {
        Optional<School> schoolOptional = schoolRepository.findById(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
        School school = schoolOptional.orElseThrow();
    }

    private Grade getGrade() {
        Optional<Grade> optionalGrade = gradeRepository.findById(4);
        return optionalGrade.orElseThrow();
    }

    private String getAcademicYear(School school) {
        return academicYearService.getAcademicYearForSchool(school);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("b36b3e2f-11a1-49b8-b82f-10c2c2bc4665"));
        return school.orElse(School.builder().build());
    }
}