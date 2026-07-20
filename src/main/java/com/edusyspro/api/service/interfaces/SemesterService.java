package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AcademicYearDTO;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.service.CustomService;

public interface SemesterService extends CustomService<Semester, Integer> {
    AcademicYearDTO getAcademicYear(int semesterId);
}
