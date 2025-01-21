package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public record AssignmentEssential(
        Long id,
        String semesterName,
        ExamType examType,
        Individual preparedBy,
        String classeName,
        Section classeSection,
        String courseName,
        String courseAbbr,
        String examName,
        LocalDate examDate,
        LocalTime startTime,
        LocalTime endTime,
        boolean passed,
        ZonedDateTime addedDate,
        ZonedDateTime updatedDate
) {
    public AssignmentDTO toDTO() {
        return AssignmentDTO.builder()
                .id(id)
                .semester(Planning.builder()
                        .semestre(Semester.builder()
                                .semesterName(semesterName)
                                .build())
                        .build())
                .exam(Exam.builder()
                        .examType(examType)
                        .build())
                .preparedBy(preparedBy)
                .classe(ClasseDTO.builder()
                        .name(classeName)
                        .grade(GradeDTO.builder()
                                .section(classeSection)
                                .build())
                        .build())
                .subject(CourseDTO.builder()
                        .course(courseName)
                        .abbr(courseAbbr)
                        .build())
                .examName(examName)
                .examDate(examDate)
                .startTime(startTime)
                .endTime(endTime)
                .passed(passed)
                .addedDate(addedDate)
                .updatedDate(updatedDate)
                .build();
    }
}
