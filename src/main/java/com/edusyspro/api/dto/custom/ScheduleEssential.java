package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalTime;

public record ScheduleEssential(
    Long id,
    String academicYear,
    String teacherFirstName,
    String teacherLastName,
    String courseName,
    String courseAbbr,
    String classeName,
    Section classeSection,
    String designation,
    Day dayOfWeek,
    LocalTime startTime,
    LocalTime endTime
) {
    public ScheduleDTO toScheduleDto() {
        return ScheduleDTO.builder()
                .id(id)
                .academicYear(AcademicYear.builder()
                        .years(academicYear)
                        .build())
                .teacher(TeacherDTO.builder()
                        .personalInfo(Individual.builder()
                                .firstName(teacherFirstName)
                                .lastName(teacherLastName)
                                .build())
                        .build())
                .course(CourseDTO.builder()
                        .abbr(courseAbbr)
                        .course(courseName)
                        .build())
                .classe(ClasseDTO.builder()
                        .name(classeName)
                        .grade(Grade.builder()
                                .section(classeSection)
                                .build())
                        .build())
                .designation(designation)
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
