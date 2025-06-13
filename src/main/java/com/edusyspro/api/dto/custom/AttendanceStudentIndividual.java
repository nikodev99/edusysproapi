package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;

public record AttendanceStudentIndividual(
        long id,
        LocalDate attendanceDate,
        AttendanceStatus attendanceStatus,
        Long studentId,
        String studentFirstName,
        String studentLastName,
        String studentImage,
        String classeName,
        String category,
        Section section
) {
    public AttendanceDTO toDto() {
        return AttendanceDTO.builder()
                .id(id)
                .attendanceDate(attendanceDate)
                .status(attendanceStatus)
                .individual(Individual.builder()
                        .id(studentId)
                        .lastName(studentLastName)
                        .firstName(studentFirstName)
                        .image(studentImage)
                        .build())
                .classeEntity(ClasseDTO.builder()
                        .name(classeName)
                        .category(category)
                        .grade(GradeDTO.builder()
                                .section(section)
                                .build())
                        .build())
                .build();
    }
}
