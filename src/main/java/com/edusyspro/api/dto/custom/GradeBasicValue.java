package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.enums.Section;

public record GradeBasicValue(
        int id,
        Section section,
        String subSection
) {
    public GradeDTO convertToDTO() {
        return GradeDTO.builder()
                .id(id)
                .section(section)
                .subSection(subSection)
                .build();
    }
}
