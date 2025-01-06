package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.Individual;

import java.util.UUID;

public record ScoreBasicValue(
        UUID studentId,
        Individual student,
        long obtainedMark
) {

    public ScoreDTO toDTO() {
        return ScoreDTO.builder()
                .obtainedMark(obtainedMark)
                .student(StudentDTO.builder()
                        .id(studentId)
                        .personalInfo(student)
                        .build())
                .build();
    }

}
