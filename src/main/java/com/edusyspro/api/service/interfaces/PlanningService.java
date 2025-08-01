package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.util.List;

public interface PlanningService {

    List<PlanningDTO> findBasicPlanningValues(String schoolId, String academicYearId);
    List<PlanningDTO> findBasicPlanningByGrade(String schoolId, Section section);
    PlanningDTO findBasicPlanningById(long planningId);
    List<PlanningDTO> findAllPlanningByClasseThroughoutTheAcademicYear(int classeId, LocalDate startDate, LocalDate endDate);
}
