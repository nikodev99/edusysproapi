package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalTime;
import java.util.UUID;

public record ScheduleEssential(
    Long id,
    String academicYear,
    UUID teacherId,
    String teacherFirstName,
    String teacherLastName,
    Integer courseId,
    String courseName,
    String courseAbbr,
    Integer classeId,
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
                        .id(teacherId)
                        .personalInfo(Individual.builder()
                                .firstName(teacherFirstName)
                                .lastName(teacherLastName)
                                .build())
                        .build())
                .course(CourseDTO.builder()
                        .id(courseId)
                        .abbr(courseAbbr)
                        .course(courseName)
                        .build())
                .classe(ClasseDTO.builder()
                        .id(classeId)
                        .name(classeName)
                        .grade(GradeDTO.builder()
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
