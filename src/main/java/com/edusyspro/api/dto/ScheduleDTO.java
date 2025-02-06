package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {
    private Long id;
    private AcademicYear academicYear;
    private ClasseDTO classe;
    private TeacherDTO teacher;
    private CourseDTO course;
    private String designation;
    private Day dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public static ScheduleDTO fromEntity(Schedule schedule) {
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .academicYear(schedule.getAcademicYear())
                .classe(ClasseDTO.fromEntity(schedule.getClasseEntity()))
                .teacher(TeacherDTO.fromEntity(schedule.getTeacher()))
                .course(CourseDTO.fromEntity(schedule.getCourse()))
                .designation(schedule.getDesignation())
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }

    public static Schedule toEntity(ScheduleDTO schedule) {
        return Schedule.builder()
                .id(schedule.getId())
                .academicYear(schedule.getAcademicYear())
                .classeEntity(ClasseDTO.toEntity(schedule.getClasse()))
                .teacher(TeacherDTO.toEntity(schedule.getTeacher()))
                .course(CourseDTO.toEntity(schedule.getCourse()))
                .designation(schedule.getDesignation())
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}
