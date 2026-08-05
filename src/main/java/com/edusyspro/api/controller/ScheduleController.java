package com.edusyspro.api.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.interfaces.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping()
    ResponseEntity<?> createScheduleEvent(@RequestBody ScheduleDTO schedule) {
        try {
            return ResponseEntity.ok(scheduleService.save(schedule));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @PutMapping
    ResponseEntity<?> updateScheduleEvent(
            @RequestBody ScheduleDTO schedule,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyTime
    ) {
        try {
            return ResponseEntity.ok(scheduleService.updateSchedule(schedule, onlyTime));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @DeleteMapping
    ResponseEntity<?> deleteScheduleEvent(@RequestBody ScheduleDTO schedule) {
        try {
            return ResponseEntity.ok(scheduleService.delete(schedule));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @GetMapping
    ResponseEntity<List<ScheduleDTO>> getAllClasseSchedules(@RequestParam int classId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scheduleService.getAllClasseSchedule(classId, Section.COLLEGE, academicYear));
    }

    @GetMapping("/classe/{classeId}")
    ResponseEntity<List<ScheduleDTO>> allClasseSchedule(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scheduleService.getAllClasseSchedule(classeId, academicYear));
    }

    @GetMapping("/course/{courseId}")
    ResponseEntity<List<ScheduleDTO>> getAllCourseSchedules(@PathVariable int courseId, @RequestParam boolean byDay) {
        return ResponseEntity.ok(scheduleService.getCourseSchedules(courseId, byDay));
    }

    @GetMapping("/teacher/{teacherId}")
    ResponseEntity<List<ScheduleDTO>> allTeacherSchedules(@PathVariable String teacherId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scheduleService.getTeacherSchedule(academicYear, teacherId));
    }

    @GetMapping("/teacher_day/{teacherId}")
    ResponseEntity<List<ScheduleDTO>> teacherSchedulesByDay(@PathVariable String teacherId, @RequestParam String academicYear, @RequestParam boolean allDay) {
        return ResponseEntity.ok(scheduleService.getTeacherScheduleByDay(academicYear, teacherId, allDay));
    }

    @GetMapping("/teachers/{schoolId}/{classeId}")
    ResponseEntity<List<?>> findClasseTeachers(
            @PathVariable int classeId,
            @PathVariable String schoolId,
            @RequestParam String academicYear
    ) {
        return ResponseEntity.ok(scheduleService.getAllClasseTeachers(classeId, schoolId, academicYear));
    }

    @GetMapping("/classe_course_hours/{courseId}")
    ResponseEntity<?> findCourseHoursByClasse(@PathVariable int courseId) {
        return ResponseEntity.ok(scheduleService.getTotalCourseHoursByClasses(courseId));
    }

    @GetMapping("/teacher_course_hours/{courseId}")
    ResponseEntity<?> findCourseHoursByTeachers(@PathVariable int courseId) {
        return ResponseEntity.ok(scheduleService.getTotalCourseHoursByTeachers(courseId));
    }
}
