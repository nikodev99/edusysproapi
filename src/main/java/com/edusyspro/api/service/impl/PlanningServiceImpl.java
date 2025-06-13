package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.dto.custom.PlanningBasic;
import com.edusyspro.api.dto.custom.PlanningEssential;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.PlanningRepository;
import com.edusyspro.api.service.interfaces.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public PlanningDTO findBasicPlanningById(long planningId) {
        return planningRepository.findPlanningById(planningId)
                .orElseThrow(() -> new NotFountException("Planning not found"))
                .toDto();
    }

    @Override
    public List<PlanningDTO> findAllPlanningByClasseThroughoutTheAcademicYear(int classeId, LocalDate startDate, LocalDate endDate) {
        return planningRepository.getClassePlanningByDates(classeId, startDate, endDate)
                .orElse(List.of())
                .stream()
                .map(PlanningBasic::toDto)
                .toList();
    }
}
