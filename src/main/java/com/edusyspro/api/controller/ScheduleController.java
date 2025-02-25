package com.edusyspro.api.controller;

import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.interfaces.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    ResponseEntity<List<ScheduleDTO>> getAllClasseSchedules(@RequestParam int classId) {
        return ResponseEntity.ok(scheduleService.getAllClasseSchedule(classId, Section.COLLEGE));
    }

    @GetMapping("/classe/{classeId}")
    ResponseEntity<List<ScheduleDTO>> allClasseSchedule(@PathVariable int classeId) {
        return ResponseEntity.ok(scheduleService.getAllClasseSchedule(classeId));
    }

    @GetMapping("/teacher/{teacherId}")
    ResponseEntity<List<ScheduleDTO>> allTeacherSchedules(@PathVariable String teacherId) {
        return ResponseEntity.ok(scheduleService.getTeacherSchedule(teacherId));
    }

    @GetMapping("/teacher_day/{teacherId}")
    ResponseEntity<List<ScheduleDTO>> teacherSchedulesByDay(@PathVariable String teacherId, @RequestParam boolean allDay) {
        return ResponseEntity.ok(scheduleService.getTeacherScheduleByDay(teacherId, allDay));
    }

    @GetMapping("/teachers/{classeId}")
    ResponseEntity<List<?>> findClasseTeachers(@PathVariable int classeId) {
        return ResponseEntity.ok(scheduleService.getAllClasseTeachers(classeId));
    }
}
