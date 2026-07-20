package com.edusyspro.api.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.ReprimandDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.dto.filters.ReprimandFilters;
import com.edusyspro.api.model.enums.PunishmentStatus;
import com.edusyspro.api.model.enums.PunishmentType;
import com.edusyspro.api.model.enums.ReprimandType;
import com.edusyspro.api.service.interfaces.ReprimandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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

    @PostMapping()
    ResponseEntity<?> saveReprimand(@RequestBody ReprimandDTO reprimand) {
        try {
            var createdReprimand = reprimandService.createReprimand(reprimand);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReprimand);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                            .message(e.getMessage())
                            .timestamp(Instant.now().toString())
                            .isError(true)
                    .build());
        }
    }

    @GetMapping("/all/{studentId}")
    ResponseEntity<?> getAllStudentReprimand(@PathVariable String studentId) {
        return ResponseEntity.ok(reprimandService.findAllReprimandsByStudentId(studentId));
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
                classeId, null, punishmentType, reprimandType, punishmentStatus,
                reprimandBetween
        );
        return ResponseEntity.ok(reprimandService.findStudentReprimands(filters, pageable));
    }

    @GetMapping("/classe/{classeId}")
    ResponseEntity<Page<ReprimandDTO>> getReprimands(
            @PathVariable Integer classeId,
            @RequestParam String academicYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) PunishmentType punishmentType,
            @RequestParam(required = false) ReprimandType reprimandType,
            @RequestParam(required = false) PunishmentStatus punishmentStatus,
            @RequestParam(required = false) LocalDate[] reprimandBetween
    ) {
        Pageable pageable = ControllerUtils.setPage(page, size);
        ReprimandFilters filters = new ReprimandFilters(
                null, UUID.fromString(academicYear),
                classeId, null, punishmentType, reprimandType, punishmentStatus,
                reprimandBetween
        );
        return ResponseEntity.ok(reprimandService.findClasseReprimands(filters, pageable));
    }

    @GetMapping("/teacher_some/{teacherId}/{schoolId}")
    ResponseEntity<?> getSomeStudentReprimandsByTeacher(@PathVariable String teacherId, @PathVariable String schoolId) {
        return ResponseEntity.ok(reprimandService.fetchSomeStudentReprimandedByTeacher(Long.parseLong(teacherId), schoolId));
    }

    @GetMapping("/teacher_all/{teacherId}")
    ResponseEntity<?> getAllStudentReprimandsByTeacher(
            @PathVariable String teacherId,
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
                null, UUID.fromString(academicYear),
                classeId, Long.parseLong(teacherId), punishmentType, reprimandType, punishmentStatus,
                reprimandBetween
        );
        return ResponseEntity.ok(reprimandService.fetchAllStudentReprimandedByTeacher(filters, pageable));
    }

    @PatchMapping("/{reprimandId}")
    ResponseEntity<?> updateReprimand(@PathVariable Long reprimandId, @RequestBody UpdateField fields) {
        try {
            int updated = reprimandService.updateReprimand(fields.field(), fields.value(), reprimandId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reprimand update failed or not found");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/punishment/{punishmentId}")
    ResponseEntity<?> updatePunishment(@PathVariable Long punishmentId, @RequestBody UpdateField fields) {
        try {
            int updated = reprimandService.updatePunishment(fields.field(), fields.value(), punishmentId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Punishment update failed or not found");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
