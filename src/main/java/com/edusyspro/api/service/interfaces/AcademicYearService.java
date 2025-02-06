package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.AcademicYear;

import java.util.List;

public interface AcademicYearService {
    List<AcademicYear> getAcademicYearsFromYear(String schoolId, int year);
    AcademicYear getCurrentAcademicYear(String schoolId);
    List<AcademicYear> getAllSchoolAcademicYear(String schoolId);
}
