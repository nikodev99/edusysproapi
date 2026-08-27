package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.enums.Section;

import java.time.ZonedDateTime;

public record GradeBasicValue(
        int id,
        Section section,
        String subSection,
        Integer gradingScaleMax,
        Integer gradingPassThreshold,
        ZonedDateTime createdAt,
        ZonedDateTime modifyAt
) {
    public GradeDTO convertToDTO() {
        return GradeDTO.builder()
                .id(id)
                .section(section)
                .subSection(subSection)
                .gradingScaleMax(gradingScaleMax)
                .gradingPassThreshold(gradingPassThreshold)
                .createdAt(createdAt)
                .modifyAt(modifyAt)
                .build();
    }
}
