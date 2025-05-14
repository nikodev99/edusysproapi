package com.edusyspro.api.service.impl;

import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.School;
import com.edusyspro.api.repository.AcademicYearRepository;
import com.edusyspro.api.service.interfaces.AcademicYearService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    @Autowired
    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public List<AcademicYear> getAcademicYearsFromYear(String schoolId, int year) {
        return academicYearRepository.findAllBeginningOfYear(UUID.fromString(schoolId), year);
    }

    @Override
    public AcademicYear getAcademicYearsFromDate(String schoolId, LocalDate date) {
        return academicYearRepository.findBeginningOfYear(UUID.fromString(schoolId), date);
    }

    @Override
    public AcademicYear getCurrentAcademicYear(String schoolId) {
        return academicYearRepository.findAcademicYearBySchoolIdAndCurrentIsTrue(UUID.fromString(schoolId));
    }

    @Override
    public AcademicYear getAcademicYearById(String academicYearId) {
        return academicYearRepository.findAcademicYearById(UUID.fromString(academicYearId))
                .orElseThrow(() -> new NotFountException("Academic Year Not found for ID: " + academicYearId));
    }

    @Override
    public List<AcademicYear> getAllSchoolAcademicYear(String schoolId) {
        return academicYearRepository.findAllBySchoolId(UUID.fromString(schoolId));
    }

    public String getAcademicYearForSchool(School school) {
        Optional<Tuple> year = academicYearRepository.findBySchool(school.getId());
        Tuple tuple = year.orElseThrow();
        LocalDate startDate = LocalDate.parse(tuple.get(0).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(tuple.get(1).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return startDate.getYear() + "-" + endDate.getYear();
    }
}
