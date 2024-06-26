package com.edusyspro.api.repository;

import com.edusyspro.api.entities.AcademicYear;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.service.AcademicYearService;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Profile("dev")
class AcademicYearRepositoryTest {

    @Autowired
    AcademicYearRepository academicYearRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    AcademicYearService academicYearService;

    @Test
    public void saveAcademicYear() {
        AcademicYear academicYear = AcademicYear.builder()
                .startDate(LocalDate.of(2023, 10, 1))
                .endDate(LocalDate.of(2024, 6, 29))
                .build();
        School school = getSchool();
        if (school.getId() != null) {
            academicYear.setSchool(school);
        }
        academicYearRepository.save(academicYear);
    }

    @Test
    public void printAcademicYear() {
        List<AcademicYear> year = academicYearRepository.findAll();
        System.out.println(year);
    }

    @Test
    public void printAnAcademicYear() {
        Optional<AcademicYear> academicYear = academicYearRepository.findById(1);
        System.out.println(academicYear.orElse(AcademicYear.builder().build()));
    }

    @Test
    public void testCurrentAcademicYear() {
        Optional<Tuple> year = academicYearRepository.findBySchool(getSchool().getId());
        Tuple t = year.orElseThrow();
        System.out.println("retrieved data=" + t);
        System.out.println("Current academic year=" + t.get(0) + "-" + t.get(1));
    }

    @Test
    public void testCurrentAcademicYearByService() {
        String currentAcademicYear = academicYearService.getAcademicYearForSchool(getSchool());
        System.out.println("Current academic year=" + currentAcademicYear);
    }

    @Test
    public void updateAcademicYear() {
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);
        AcademicYear a = AcademicYear.builder()
                .startDate(startDate)
                .endDate(endDate)
                .school(getSchool())
                .build();
        int updatedTimes = academicYearRepository.updateAcademicYearById(
                a.getStartDate(), a.getEndDate(),a.getSchool(), 1
        );
        System.out.println("updated times=" + updatedTimes);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("27a58e8a-a588-45dd-917e-6b690acd4b22"));
        return school.orElseThrow();
    }

}