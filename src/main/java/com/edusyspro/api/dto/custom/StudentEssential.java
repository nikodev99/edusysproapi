package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;

import java.math.BigDecimal;
import java.util.UUID;

public record StudentEssential (
    UUID id,
    Individual personalInfo,
    String dadName,
    String momName,
    int classeId,
    String classe,
    String classeCategory,
    Section grade,
    BigDecimal monthCost,
    UUID schoolId,
    String name,
    String abbr,
    String websiteURL
) {
    public StudentDTO toStudent() {
        return StudentDTO.builder()
                .id(id)
                .personalInfo(personalInfo)
                .dadName(dadName)
                .momName(momName)
                .classe(ClasseDTO.builder()
                        .id(classeId)
                        .name(classe)
                        .category(classeCategory)
                        .grade(GradeDTO.builder()
                                .section(grade)
                                .build())
                        .monthCost(monthCost)
                        .build())
                .school(School.builder()
                        .id(schoolId)
                        .name(name)
                        .abbr(abbr)
                        .websiteURL(websiteURL)
                        .build())
                .build();
    }
}
