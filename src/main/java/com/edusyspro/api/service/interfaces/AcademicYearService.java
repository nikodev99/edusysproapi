package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AcademicYearDTO;
import com.edusyspro.api.dto.custom.UpdateField;

import java.time.LocalDate;
import java.util.List;

public interface AcademicYearService {
    Boolean addAcademicYear(AcademicYearDTO academicYear);
    int updateAcademicYearField(String academicYearId, UpdateField field);
    List<AcademicYearDTO> getAcademicYearsFromYear(String schoolId, int year);
    AcademicYearDTO getAcademicYearsFromDate(String schoolId, LocalDate date);
    AcademicYearDTO getCurrentAcademicYear(String schoolId);
    AcademicYearDTO getAcademicYearById(String academicYearId);
    List<AcademicYearDTO> getAllSchoolAcademicYear(String schoolId);
}
