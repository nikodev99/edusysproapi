package com.edusyspro.api.dto;

import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradeDTO {
    private Integer id;
    private Section section;
    private String subSection;
    private List<PlanningDTO> planning;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static GradeDTO fromEntity(Grade entity) {
        return GradeDTO.builder()
                .id(entity.getId())
                .section(entity.getSection())
                .subSection(entity.getSubSection())
                .planning(entity.getPlanning().stream().map(PlanningDTO::fromEntity).toList())
                .school(entity.getSchool())
                .createdAt(entity.getCreatedAt())
                .modifyAt(entity.getModifyAt())
                .build();
    }

    public static Grade toEntity(GradeDTO entity) {
        return Grade.builder()
                .id(entity.getId())
                .section(entity.getSection())
                .subSection(entity.getSubSection())
                .planning(entity.getPlanning().stream().map(PlanningDTO::toEntity).toList())
                .school(entity.getSchool())
                .createdAt(entity.getCreatedAt())
                .modifyAt(entity.getModifyAt())
                .build();
    }
}
