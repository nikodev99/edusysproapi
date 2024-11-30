package com.edusyspro.api.controller;

import com.edusyspro.api.model.Schedule;
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

    @GetMapping("/teacher/{teacherId}")
    ResponseEntity<List<Schedule>> allTeacherSchedules(@PathVariable String teacherId) {
        return ResponseEntity.ok(scheduleService.getTeacherSchedule(teacherId));
    }

    @GetMapping("/teacher_day/{teacherId}")
    ResponseEntity<List<Schedule>> teacherSchedulesByDay(@PathVariable String teacherId, @RequestParam boolean allDay) {
        return ResponseEntity.ok(scheduleService.getTeacherScheduleByDay(teacherId, allDay));
    }
}
