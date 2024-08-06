package com.edusyspro.api.repository;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.School;
import com.edusyspro.api.service.impl.AcademicYearService;
import com.edusyspro.api.utils.MockUtils;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                .startDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2025, 6, 29))
                .current(true)
                .build();
        School school = MockUtils.SCHOOL_MOCK;
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
    public void testCurrentAcademicYear() {
        Optional<Tuple> year = academicYearRepository.findBySchool(MockUtils.SCHOOL_MOCK.getId());
        Tuple t = year.orElseThrow();
        System.out.println("retrieved data=" + t);
        System.out.println("Current academic year=" + t.get(0) + "-" + t.get(1));
    }

    @Test
    public void testCurrentAcademicYearByService() {
        String currentAcademicYear = academicYearService.getAcademicYearForSchool(MockUtils.SCHOOL_MOCK);
        System.out.println("Current academic year=" + currentAcademicYear);
    }

    @Test
    public void updateAcademicYear() {
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);
        AcademicYear a = AcademicYear.builder()
                .startDate(startDate)
                .endDate(endDate)
                .school(MockUtils.SCHOOL_MOCK)
                .build();
        int updatedTimes = academicYearRepository.updateAcademicYearById(
                a.getStartDate(), a.getEndDate(),a.getSchool(), 1
        );
        System.out.println("updated times=" + updatedTimes);
    }

}