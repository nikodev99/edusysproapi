package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.enums.Section;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record ClasseEssential(
        Integer id,
        String name,
        String category,
        Section section,
        String subSection,
        Integer roomNumber,
        String department,
        String code,
        BigDecimal monthCost,
        ZonedDateTime createdAt
) {
    public ClasseDTO convertToDTO() {
        return ClasseDTO.builder()
                .id(id)
                .name(name)
                .category(category)
                .grade(GradeDTO.builder()
                        .section(section)
                        .subSection(subSection)
                        .build())
                .department(DepartmentDTO.builder()
                        .name(department)
                        .code(code)
                        .build())
                .roomNumber(roomNumber)
                .monthCost(monthCost)
                .createdAt(createdAt)
                .build();
    }
}
