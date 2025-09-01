package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.Planning;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PlanningService {
    Boolean addPlanning(PlanningDTO planning);
    Boolean updatePlanning(PlanningDTO planning, long planningId);
    Map<String, Boolean> deletePlanning(long planningId);
    List<PlanningDTO> findBasicPlanningValues(String schoolId, String academicYearId);
    List<PlanningDTO> findBasicPlanningByGrade(String schoolId, Section section);
    PlanningDTO findBasicPlanningById(long planningId);
    List<PlanningDTO> findAllPlanningByClasseThroughoutTheAcademicYear(int classeId, LocalDate startDate, LocalDate endDate);
}
