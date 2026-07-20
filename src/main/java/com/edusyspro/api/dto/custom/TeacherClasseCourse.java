package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TeacherClasseCourse(
        UUID id,
        Individual personalInfo,
        Long contractId,
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
                .contract(EmployeeContractDTO.builder()
                        .id(contractId)
                        .startDate(hireDate)
                        .build())
                .aClasses(List.of(TeacherClasseDTO.builder()
                                .classe(ClasseDTO.builder()
                                        .id(classeId)
                                        .name(classeName)
                                        .build())
                        .build()))
                .courses(List.of(TeacherCourseDTO.builder()
                                .course(CourseDTO.builder()
                                        .id(courseId)
                                        .course(courseName)
                                        .build())
                        .build()))
                .build();
    }

    public TeacherClasseDTO toTeacherClasse() {
        return TeacherClasseDTO.builder()
                .teacher(toTeacher())
                .build();
    }
}
