package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.AcademicYear;

import java.time.LocalDate;
import java.util.List;

public interface AcademicYearService {
    List<AcademicYear> getAcademicYearsFromYear(String schoolId, int year);
    AcademicYear getAcademicYearsFromDate(String schoolId, LocalDate date);
    AcademicYear getCurrentAcademicYear(String schoolId);
    AcademicYear getAcademicYearById(String academicYearId);
    List<AcademicYear> getAllSchoolAcademicYear(String schoolId);
}
