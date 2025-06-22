package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.service.interfaces.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    ResponseEntity<?> saveAttendance(
            @RequestBody List<AttendanceDTO> attendances,
            @RequestParam int classe,
            @RequestParam String date
    ) {
        LocalDate dateOfTheDay = ControllerUtils.parseDate(date);
        return ResponseEntity.ok(
                attendanceService.saveAllAttendances(attendances, classe, dateOfTheDay)
        );
    }

    @PutMapping
    ResponseEntity<Boolean> modifyingAttendanceStatus(@RequestBody List<AttendanceDTO> attendances) {
        return ResponseEntity.ok(attendanceService.updateAllAttendances(attendances));
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

    @GetMapping("/all_classe/{classeId}")
    ResponseEntity<?> getAllClasseStudentAttendanceOfTheDay(
            @PathVariable Integer classeId,
            @RequestParam String academicYear,
            @RequestParam(required = false) String dateOfTheDay
    ) {
        return ResponseEntity.ok(attendanceService.getAllClasseAttendancesPerDate(
                classeId,
                academicYear,
                ControllerUtils.parseDate(dateOfTheDay)
        ));
    }

    @GetMapping("/status_count/{studentId}")
    ResponseEntity<?> getStudentAttendanceStatusCounts(@PathVariable long studentId, @RequestParam String academicYear) {
        return ResponseEntity.ok(attendanceService.getStudentAttendanceCount(studentId, academicYear));
    }

    @GetMapping("/all_school/{schoolId}")
    ResponseEntity<?> getAllSchoolStudentAttendanceOfTheDay(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam String academicYear,
            @RequestParam(required = false) String dateOfTheDay,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortCriteria
    ) {
        LocalDate date = ControllerUtils.parseDate(dateOfTheDay);
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        if(search == null) {
            return ResponseEntity.ok(attendanceService.getAllSchoolPerDateAttendances(
                    schoolId, academicYear, date, pageable
            ));
        }else {
            return ResponseEntity.ok(attendanceService.getAllSchoolPerDateAttendances(
                    schoolId, academicYear, date, search, pageable
            ));
        }
    }

    @GetMapping("/classe_{classeId}")
    ResponseEntity<?> getClasseAttendanceStatusCounts(@PathVariable int classeId, @RequestParam String academicYear, String date) {
        LocalDate dateOfTheDay = ControllerUtils.parseDate(date);
        return ResponseEntity.ok(attendanceService.getClasseAttendanceCount(classeId, academicYear, dateOfTheDay));
    }

    @GetMapping("/classe_status/{classeId}")
    ResponseEntity<?> getClasseStatusAttendanceStatusCounts(
            @PathVariable int classeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String academicYear,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(attendanceService.getStudentAttendanceStatus(classeId, academicYear, pageable));
    }

    @GetMapping("/classe_status_search/{classeId}")
    ResponseEntity<?> getClasseStatusAttendanceStatusCounts(
            @PathVariable int classeId,
            @RequestParam String academicYear,
            @RequestParam String search
    ) {
        return ResponseEntity.ok(attendanceService.getStudentAttendanceStatus(classeId, academicYear, search));
    }

    @GetMapping("/classe_good/{classeId}")
    ResponseEntity<?> getGoodStudentAttendanceStatusCounts(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(attendanceService.getBestStudentAtAttendance(classeId, academicYear));
    }

    @GetMapping("/classe_worst/{classeId}")
    ResponseEntity<?> getWorstStudentAttendanceStatusCounts(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(attendanceService.getWorstStudentAtAttendance(classeId, academicYear));
    }

    @GetMapping("/school_ranking/{schoolId}")
    ResponseEntity<?> getSchoolStudentRanking(
            @PathVariable String schoolId,
            @RequestParam String academicYear,
            @RequestParam(defaultValue = "false") boolean isWorst
    ) {
        return ResponseEntity.ok(attendanceService.getStudentAtAttendanceRanking(schoolId, academicYear, isWorst));
    }

    @GetMapping("/school_status/{academicYear}")
    ResponseEntity<?> getSchoolAttendanceStatusCounts(@PathVariable String academicYear, @RequestParam(required = false) String date) {
        LocalDate dateOfTheDay = ControllerUtils.parseDate(date);
        return ResponseEntity.ok(attendanceService.getSchoolAttendanceCount(academicYear, dateOfTheDay));
    }

    @GetMapping("/classe_stat/{classeId}")
    ResponseEntity<?> getClasseAttendanceStatPerStatus(
            @PathVariable int classeId,
            @RequestParam String academicYear,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        LocalDate start = ControllerUtils.parseDate(startDate);
        LocalDate end = ControllerUtils.parseDate(endDate);

        return ResponseEntity.ok(attendanceService.getClasseAttendanceStats(classeId, academicYear, start, end));
    }

    @GetMapping("/school_stat/{schoolId}")
    ResponseEntity<?> getSchoolAttendanceStatPerStatus(
            @PathVariable String schoolId,
            @RequestParam String academicYear,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        LocalDate start = ControllerUtils.parseDate(startDate);
        LocalDate end = ControllerUtils.parseDate(endDate);
        return ResponseEntity.ok(attendanceService.getSchoolAttendanceStats(schoolId, academicYear, start, end));
    }

}
