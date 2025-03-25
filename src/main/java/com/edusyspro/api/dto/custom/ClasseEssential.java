package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.enums.Section;

import java.time.ZonedDateTime;

public record ClasseEssential(
        int id,
        String name,
        String category,
        Section section,
        String subSection,
        int roomNumber,
        double monthCost,
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
                .roomNumber(roomNumber)
                .monthCost(monthCost)
                .createdAt(createdAt)
                .build();
    }
}
