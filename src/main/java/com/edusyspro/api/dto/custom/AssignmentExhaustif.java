package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.ExamType;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.AssignmentType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public record AssignmentExhaustif(
        Long id,
        Long planningId,
        Long examId,
        ExamType examType,
        LocalDate examStart,
        LocalDate examEnd,
        Long teacherId,
        Integer classeId,
        Integer courseId,
        String courseName,
        String courseAbbr,
        String courseCode,
        String examName,
        LocalDate examDate,
        LocalTime startTime,
        LocalTime endTime,
        AssignmentType type,
        Boolean passed,
        Integer coefficient,
        ZonedDateTime addedDate,
        ZonedDateTime updatedDate
) {

    public AssignmentDTO toDTO() {
        return AssignmentDTO.builder()
                .id(id)
                .semester(PlanningDTO.builder()
                        .id(planningId)
                        .build())
                .preparedBy(Individual.builder()
                        .id(teacherId)
                        .build())
                .classe(ClasseDTO.builder()
                        .id(classeId)
                        .build())
                .subject(CourseDTO.builder()
                        .id(courseId)
                        .course(courseName)
                        .abbr(courseAbbr)
                        .department(DepartmentDTO.builder()
                                .code(courseCode)
                                .build())
                        .build())
                .exam(ExamDTO.builder()
                        .id(examId)
                        .examType(examType)
                        .startDate(examStart)
                        .endDate(examEnd)
                        .build())
                .examName(examName)
                .examDate(examDate)
                .startTime(startTime)
                .endTime(endTime)
                .coefficient(coefficient)
                .passed(passed)
                .type(type)
                .addedDate(addedDate)
                .updatedDate(updatedDate)
                .build();
    }

}
