package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.Planning;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface PlanningService {
    Boolean addPlanning(PlanningDTO planning);
    Boolean updatePlanning(PlanningDTO planning, long planningId);
    Map<String, Boolean> deletePlanning(long planningId);
    List<PlanningDTO> findBasicPlanningValues(String schoolId, String academicYearId);
    List<PlanningDTO> findBasicPlanningByGrade(String schoolId, Section section);
    List<PlanningDTO> findBasicPlanningByGradeAndPeriod(int gradeId, String academicYear, ZonedDateTime startDate, ZonedDateTime endDate);
    List<PlanningDTO> findBasicPlanningByGradeAndPeriod(int gradeId, String academicYear, ZonedDateTime endDate);
    List<PlanningDTO> findBasicPlanningByGradeOfAMonth(int gradeId, String academicYear);
    List<PlanningDTO> findBasicDynamicPlanningByGradeOfAMonth(int gradeId, String academicYear);
    PlanningDTO findBasicPlanningById(long planningId);
    List<PlanningDTO> findAllPlanningByClasseThroughoutTheAcademicYear(int classeId, ZonedDateTime startDate, ZonedDateTime endDate);
}
