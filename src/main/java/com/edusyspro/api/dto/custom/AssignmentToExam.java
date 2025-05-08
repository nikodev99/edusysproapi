package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.model.enums.AssignmentType;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.time.LocalTime;

public record AssignmentToExam(
        Long id,
        String semesterName,
        String planningName,
        long teacherId,
        String teacherFirstName,
        String teacherLastName,
        String teacherImage,
        Integer classeId,
        String classeName,
        Section classeSection,
        Integer courseId,
        String courseName,
        String courseAbbr,
        String examName,
        LocalDate examDate,
        LocalTime startTime,
        LocalTime endTime,
        AssignmentType type,
        boolean passed,
        int coefficient
) {

    public AssignmentDTO toDTO() {
        return AssignmentDTO.builder()
                .id(id)
                .semester(PlanningDTO.builder()
                        .designation(planningName)
                        .semestre(Semester.builder()
                                .semesterName(semesterName)
                                .build())
                        .build())
                .preparedBy(Individual.builder()
                        .id(teacherId)
                        .firstName(teacherFirstName)
                        .lastName(teacherLastName)
                        .image(teacherImage)
                        .build())
                .classe(ClasseDTO.builder()
                        .id(classeId)
                        .name(classeName)
                        .grade(GradeDTO.builder()
                                .section(classeSection)
                                .build())
                        .build())
                .subject(CourseDTO.builder()
                        .id(courseId)
                        .course(courseName)
                        .abbr(courseAbbr)
                        .build())
                .examName(examName)
                .examDate(examDate)
                .startTime(startTime)
                .endTime(endTime)
                .coefficient(coefficient)
                .passed(passed)
                .type(type)
                .build();
    }

}
