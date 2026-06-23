package com.edusyspro.api.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.TeachingReportDTO;
import com.edusyspro.api.service.interfaces.TeachingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final TeachingReportService teachingReportService;

    @Autowired
    private ReportController(TeachingReportService teachingReportService) {
        this.teachingReportService = teachingReportService;
    }

    @PostMapping
    ResponseEntity<MessageResponse> saveReport(@RequestBody TeachingReportDTO report) {
        try {
            teachingReportService.saveReport(report);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Rapport du " + report.getSessionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " soumit avec succès")
                    .isError(false)
                    .timestamp(Instant.now().toString())
                    .build());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .build());
        }

    }

    @GetMapping("/week/{teacherId}")
    ResponseEntity<?> getWeekReport(@PathVariable String teacherId, @RequestParam String startDate, @RequestParam String endDate) {
        return ResponseEntity.ok(teachingReportService.getBasicReport(
                teacherId,
                ControllerUtils.parseDate(startDate),
                ControllerUtils.parseDate(endDate)
        ));
    }

    @GetMapping("/{reportId}")
    ResponseEntity<TeachingReportDTO> getReport(@PathVariable Long reportId) {
        return ResponseEntity.ok(teachingReportService.getDetailedReport(reportId));
    }
}
