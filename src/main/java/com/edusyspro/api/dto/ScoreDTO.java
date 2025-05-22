package com.edusyspro.api.dto;

import com.edusyspro.api.model.Score;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreDTO {
    private Long id;
    private AssignmentDTO assignment;
    private StudentDTO student;
    private Long obtainedMark;
    private Boolean isPresent;

    public Score toDTO() {
        return Score.builder()
                .id(id)
                .assignment(assignment.toMergeEntity())
                .isPresent(isPresent)
                .obtainedMark(obtainedMark)
                .studentEntity(StudentDTO.toMergeEntity(student))
                .build();
    }
}
