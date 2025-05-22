package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.StudentDTO;

import java.util.UUID;

public record ScoreBasic(
        Long scoreId,
        UUID studentId,
        Long assignmentId,
        Long obtainedMark
) {
    public ScoreDTO toDTO() {
        return ScoreDTO.builder()
                .id(scoreId)
                .obtainedMark(obtainedMark)
                .student(StudentDTO.builder()
                        .id(studentId)
                        .build())
                .assignment(
                        AssignmentDTO.builder()
                                .id(assignmentId)
                                .build()
                )
                .build();
    }
}
