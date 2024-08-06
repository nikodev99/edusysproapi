package com.edusyspro.api.service.impl;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.School;
import com.edusyspro.api.repository.AcademicYearRepository;
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
