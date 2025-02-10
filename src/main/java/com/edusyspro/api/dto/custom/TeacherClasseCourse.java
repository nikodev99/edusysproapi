package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.model.Individual;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TeacherClasseCourse(
        UUID id,
        Individual personalInfo,
        LocalDate hireDate,
        Integer classeId,
        String classeName,
        Integer courseId,
        String courseName
) {
    public TeacherDTO toTeacher() {
        return TeacherDTO.builder()
                .id(id)
                .personalInfo(personalInfo)
                .hireDate(hireDate)
                .aClasses(List.of(ClasseDTO.builder()
                                .id(classeId)
                                .name(classeName)
                        .build()))
                .courses(List.of(CourseDTO.builder()
                                .id(courseId)
                                .course(courseName)
                        .build()))
                .build();
    }
}
