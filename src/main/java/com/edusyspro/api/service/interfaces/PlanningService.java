package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.PlanningDTO;

import java.util.List;

public interface PlanningService {

    List<PlanningDTO> findBasicPlanningValues(String schoolId, String academicYearId);

}
