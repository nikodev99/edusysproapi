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
    private Long assignmentCount;
    private StudentDTO student;
    private Double obtainedMark;
    private Double shrinkMark;
    private Boolean isPresent;

    public Score toEntity() {
        return Score.builder()
                .id(id)
                .assignment(assignment.toMergeEntity())
                .isPresent(isPresent)
                .obtainedMark(obtainedMark)
                .studentEntity(StudentDTO.toMergeEntity(student))
                .build();
    }

    public static ScoreDTO toDto(Score score) {
        return ScoreDTO.builder()
                .id(score.getId())
                .assignment(AssignmentDTO.builder().id(score.getAssignment().getId()).build())
                .student(StudentDTO.builder()
                        .id(score.getStudentEntity().getId())
                        .personalInfo(score.getStudentEntity().getPersonalInfo())
                        .build()
                )
                .obtainedMark(score.getObtainedMark())
                .isPresent(score.getIsPresent())
                .build();
    }
}
