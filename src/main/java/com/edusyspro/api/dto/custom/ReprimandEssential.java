package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.ReprimandDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Punishment;
import com.edusyspro.api.model.enums.ReprimandType;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.util.UUID;

public record ReprimandEssential(
        Long id,
        UUID studentId,
        Individual student,
        String studentClasse,
        Section studentSection,
        LocalDate reprimandDate,
        ReprimandType type,
        String description,
        Individual issueBy,
        Punishment punishment
) {
    public ReprimandDTO toDTO() {
        return ReprimandDTO.builder()
                .id(id)
                .student(EnrollmentDTO.builder()
                        .classe(ClasseDTO.builder()
                                .name(studentClasse)
                                .grade(Grade.builder()
                                        .section(studentSection)
                                        .build())
                                .build()
                        )
                        .student(StudentDTO.builder()
                                .id(studentId)
                                .personalInfo(student)
                                .build())
                        .build())
                .type(type)
                .description(description)
                .issuedBy(issueBy)
                .punishment(punishment)
                .build();
    }
}
