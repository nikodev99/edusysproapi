package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.dto.custom.PlanningBasic;
import com.edusyspro.api.dto.custom.PlanningEssential;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.PlanningRepository;
import com.edusyspro.api.service.interfaces.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanningServiceImpl implements PlanningService {

    private final PlanningRepository planningRepository;

    @Autowired
    public PlanningServiceImpl(PlanningRepository planningRepository) {
        this.planningRepository = planningRepository;
    }

    @Override
    public List<PlanningDTO> findBasicPlanningValues(String schoolId, String academicYearId) {
        return planningRepository.findPlanningBasicValues(
                UUID.fromString(schoolId),
                UUID.fromString(academicYearId)
        ).stream()
        .map(PlanningBasic::toDto)
        .toList();

    }

    @Override
    public List<PlanningDTO> findBasicPlanningByGrade(String schoolId, Section section) {
        return planningRepository.findPlanningsByGrade(UUID.fromString(schoolId), section).stream()
                .map(PlanningEssential::toDto)
                .toList();
    }
}
