package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
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
}
