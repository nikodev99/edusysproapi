package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.Individual;

import java.util.UUID;

public record ScoreBasicValue(
        UUID studentId,
        String studentFirstName,
        String studentLastName,
        String studentImage,
        String studentReference,
        long obtainedMark
) {

    public ScoreDTO toDTO() {
        return ScoreDTO.builder()
                .obtainedMark(obtainedMark)
                .student(StudentDTO.builder()
                        .id(studentId)
                        .personalInfo(Individual.builder()
                                .firstName(studentFirstName)
                                .lastName(studentLastName)
                                .image(studentImage)
                                .build())
                        .reference(studentReference)
                        .build())
                .build();
    }

}
