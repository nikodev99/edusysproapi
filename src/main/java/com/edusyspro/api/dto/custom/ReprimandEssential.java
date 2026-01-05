package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Punishment;
import com.edusyspro.api.model.enums.ReprimandType;
import com.edusyspro.api.model.enums.Section;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ReprimandEssential(
        Long id,
        UUID academicYearId,
        String academicYear,
        UUID studentId,
        String studentLastName,
        String studentFirstName,
        String studentImage,
        String studentReference,
        Integer classeId,
        String studentClasse,
        Section studentSection,
        ZonedDateTime reprimandDate,
        ReprimandType type,
        String description,
        Long issuerId,
        String issuerFirstName,
        String issuerLastName,
        String issuerImage,
        String reference,
        Punishment punishment
) {
    public ReprimandDTO toDTO() {
        return ReprimandDTO.builder()
                .id(id)
                .student(EnrollmentDTO.builder()
                        .academicYear(AcademicYearDTO.builder()
                                .id(academicYearId)
                                .years(academicYear)
                                .build())
                        .student(StudentDTO.builder()
                                .id(studentId)
                                .personalInfo(Individual.builder()
                                        .firstName(studentFirstName)
                                        .lastName(studentLastName)
                                        .image(studentImage)
                                        .reference(studentReference)
                                        .build())
                                .build())
                        .classe(ClasseDTO.builder()
                                .id(classeId)
                                .name(studentClasse)
                                .grade(GradeDTO.builder()
                                        .section(studentSection)
                                        .build())
                                .build())
                        .build())
                .reprimandDate(reprimandDate)
                .type(type)
                .description(description)
                .issuedBy(Individual.builder()
                        .id(issuerId)
                        .firstName(issuerFirstName)
                        .lastName(issuerLastName)
                        .image(issuerImage)
                        .reference(reference)
                        .build())
                .punishment(PunishmentDTO.toDto(punishment))
                .build();
    }
}
