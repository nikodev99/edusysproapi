package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.model.enums.AssignmentType;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AssignmentToExam(
        Long id,
        Semester semester,
        UUID academicYearId,
        Long planningId,
        String planningName,
        Long examId,
        Long teacherId,
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
        Boolean passed,
        Integer coefficient
) {

    public AssignmentDTO toDTO() {
        return AssignmentDTO.builder()
                .id(id)
                .semester(PlanningDTO.builder()
                        .id(planningId)
                        .designation(planningName)
                        .semestre(semester)
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
                .exam(ExamDTO.builder()
                        .id(examId)
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
