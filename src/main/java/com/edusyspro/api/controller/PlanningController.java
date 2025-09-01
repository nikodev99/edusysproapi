package com.edusyspro.api.controller;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.helper.log.L;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.interfaces.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    private final PlanningService planningService;

    @Autowired
    public PlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @PostMapping
    ResponseEntity<?> savePlanning(@RequestBody PlanningDTO planning) {
        return ResponseEntity.ok(planningService.addPlanning(planning));
    }

    @PutMapping("/{planningId}")
    ResponseEntity<?> updatePlanning(@RequestBody PlanningDTO planning, @PathVariable long planningId) {
        return ResponseEntity.ok(planningService.updatePlanning(planning, planningId));
    }

    @DeleteMapping("/{planningId}")
    ResponseEntity<?> deletePlanning(@PathVariable long planningId) {
        L.info("deletePlanning planningId={}", planningId);
        try {
            return ResponseEntity.ok(planningService.deletePlanning(planningId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @RequestMapping("/basic/{schoolId}")
    ResponseEntity<List<PlanningDTO>> fetchBasicPlanningValues(@PathVariable String schoolId, @RequestParam String academicYear) {
        return ResponseEntity.ok(planningService.findBasicPlanningValues(schoolId, academicYear));
    }

    @RequestMapping(value = {"/{schoolId}", "/all/{schoolId}"})
    ResponseEntity<?> fetchPlanningByGrades(@PathVariable String schoolId, @RequestParam Section section) {
        return ResponseEntity.ok(planningService.findBasicPlanningByGrade(schoolId, section));
    }

}
