package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.ReprimandDTO;
import com.edusyspro.api.dto.filters.ReprimandFilters;
import com.edusyspro.api.model.enums.PunishmentStatus;
import com.edusyspro.api.model.enums.PunishmentType;
import com.edusyspro.api.model.enums.ReprimandType;
import com.edusyspro.api.service.interfaces.ReprimandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/blame"})
public class ReprimandController {

    private final ReprimandService reprimandService;

    @Autowired
    public ReprimandController(ReprimandService reprimandService) {
        this.reprimandService = reprimandService;
    }

    @GetMapping("/{studentId}")
    ResponseEntity<Page<ReprimandDTO>> getReprimands(
            @PathVariable String studentId,
            @RequestParam String academicYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer classeId,
            @RequestParam(required = false) PunishmentType punishmentType,
            @RequestParam(required = false) ReprimandType reprimandType,
            @RequestParam(required = false) PunishmentStatus punishmentStatus,
            @RequestParam(required = false) LocalDate[] reprimandBetween
    ) {
        Pageable pageable = ControllerUtils.setPage(page, size);
        ReprimandFilters filters = new ReprimandFilters(
                UUID.fromString(studentId), UUID.fromString(academicYear),
                classeId, punishmentType, reprimandType, punishmentStatus,
                reprimandBetween
        );
        return ResponseEntity.ok(reprimandService.findStudentReprimand(filters, pageable));
    }

    @GetMapping("/teacher_some/{teacherId}")
    ResponseEntity<?> getSomeStudentReprimandsByTeacher(@PathVariable String teacherId) {
        return ResponseEntity.ok(reprimandService.fetchSomeStudentReprimandedByTeacher(Long.parseLong(teacherId)));
    }

    @GetMapping("/teacher_all/{teacherId}")
    ResponseEntity<?> getAllStudentReprimandsByTeacher(
            @PathVariable String teacherId,
            @RequestParam String academicYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reprimandService.fetchAllStudentReprimandedByTeacher(
                Long.parseLong(teacherId), academicYear, ControllerUtils.setPage(page, size)
        ));
    }

}
