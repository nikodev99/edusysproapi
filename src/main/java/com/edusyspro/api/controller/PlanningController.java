package com.edusyspro.api.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.service.interfaces.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    private final PlanningService planningService;

    @Autowired
    public PlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @RequestMapping("/basic")
    ResponseEntity<List<PlanningDTO>> fetchBasicPlanningValues(@RequestParam String academicYear) {
        return ResponseEntity.ok(planningService.findBasicPlanningValues(ConstantUtils.SCHOOL_ID, academicYear));
    }

}
