package com.edusyspro.api.dto;

import com.edusyspro.api.model.Planning;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;
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
public class GradeDTO {
    private int id;
    private Section section;
    private String subSection;
    private List<Planning> planning;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
