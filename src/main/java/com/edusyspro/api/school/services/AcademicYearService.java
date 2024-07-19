package com.edusyspro.api.school.services;

import com.edusyspro.api.school.entities.AcademicYear;
import com.edusyspro.api.school.entities.School;
import com.edusyspro.api.school.repos.AcademicYearRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    @Autowired
    public AcademicYearService(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    public AcademicYear getAcademicYear(String schoolId) {
        return academicYearRepository.findAcademicYearBySchoolIdAndCurrentIsTrue(UUID.fromString(schoolId));
    }

    public String getAcademicYearForSchool(School school) {
        Optional<Tuple> year = academicYearRepository.findBySchool(school.getId());
        Tuple tuple = year.orElseThrow();
        LocalDate startDate = LocalDate.parse(tuple.get(0).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(tuple.get(1).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return startDate.getYear() + "-" + endDate.getYear();
    }

}
