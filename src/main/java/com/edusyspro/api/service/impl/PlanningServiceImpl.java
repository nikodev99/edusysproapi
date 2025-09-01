package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.dto.custom.PlanningBasic;
import com.edusyspro.api.dto.custom.PlanningEssential;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.helper.log.L;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.PlanningRepository;
import com.edusyspro.api.service.interfaces.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PlanningServiceImpl implements PlanningService {

    private final PlanningRepository planningRepository;

    @Autowired
    public PlanningServiceImpl(PlanningRepository planningRepository) {
        this.planningRepository = planningRepository;
    }

    @Override
    public Boolean addPlanning(PlanningDTO planning) {
        var savedPlanning = planningRepository.save(PlanningDTO.toEntityWithGrade(planning));
        return savedPlanning.getId() != null;
    }

    @Override
    public Boolean updatePlanning(PlanningDTO planning, long planningId) {
        int updated = planningRepository.updatePlanning(
                planning.getSemestre(),
                planning.getDesignation(),
                planning.getTermStartDate(),
                planning.getTermEndDate(),
                planningId
        );
        return updated > 0;
    }

    @Override
    public Map<String, Boolean> deletePlanning(long planningId) {
        try {
            // Check if the planning exists before attempting to delete
            boolean exists = planningRepository.existsById(planningId);
            if (!exists) {
                L.warn("Attempted to delete non-existent planning with ID: {}", planningId);
                return Map.of("status", false);
            }

            planningRepository.deletePlanningById(planningId);
            L.info("Successfully deleted planning with ID: {}", planningId);
            return Map.of("status", true);

        } catch (DataAccessException e) {
            // Handle database-specific exceptions
            L.error("Database error while deleting planning with ID {}: {}", planningId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete planning due to database error", e);

        } catch (Exception e) {
            // Handle any other unexpected exceptions
            L.error("Unexpected error while deleting planning with ID {}: {}", planningId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete planning due to unexpected error", e);
        }
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
