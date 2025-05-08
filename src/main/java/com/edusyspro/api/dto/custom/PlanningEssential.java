package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Semester;

import java.time.LocalDate;

public record PlanningEssential(
        long id,
        String designation,
        LocalDate termStartDate,
        LocalDate termEndDate,
        Integer semestreId,
        String semestre,
        String academicYear
) {
    public PlanningDTO toDto() {
        return PlanningDTO.builder()
                .id(id)
                .designation(designation)
                .termStartDate(termStartDate)
                .termEndDate(termEndDate)
                .semestre(Semester.builder()
                        .semesterId(semestreId)
                        .semesterName(semestre)
                        .academicYear(AcademicYear.builder()
                                .years(academicYear)
                                .build())
                        .build())
                .build();
    }
}
