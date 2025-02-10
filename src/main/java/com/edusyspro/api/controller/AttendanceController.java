package com.edusyspro.api.controller;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.service.interfaces.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/{studentId}")
    ResponseEntity<Page<AttendanceDTO>> getStudentAttendance(
            @PathVariable long studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String academicYear
    ) {
        return ResponseEntity.ok(attendanceService.getStudentAttendancesByAcademicYear(
           studentId, academicYear, PageRequest.of(page, size)
        ));
    }

    @GetMapping("/all/{studentId}")
    ResponseEntity<List<AttendanceDTO>> getAllStudentAttendances(@PathVariable long studentId, @RequestParam String academicYear) {
        return ResponseEntity.ok(attendanceService.getStudentAttendances(studentId, academicYear));
    }

    @GetMapping("/classe_{classeId}")
    ResponseEntity<?> getClasseAttendanceStatusCounts(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(attendanceService.getClasseAttendanceCount(classeId, academicYear));
    }

}
