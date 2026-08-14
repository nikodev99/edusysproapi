package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Section;

import java.util.UUID;

public record StudentStats(
        UUID studentId,
        String firstName,
        String lastName,
        String image,
        String reference,
        Integer classeId,
        String classeName,
        Integer gradeId,
        Section section,
        String subSection,
        Double weightedMarkSum,
        Long coefficientSum,
        Long assignmentCount
) {
    public double weightedAverage() {
        return coefficientSum == 0 ? 0 : weightedMarkSum / coefficientSum;
    }

    // Adjust this to your actual ScoreDTO constructor/fields —
    // I'd suggest exposing weightedAverage() and assignmentCount()
    // in ScoreDTO instead of a raw sum, so the UI can show "16.8/20 (3 devoirs)".
    public ScoreDTO toScoreDTO() {
        return ScoreDTO.builder()
                .assignmentCount(assignmentCount)
                .student(StudentDTO.builder()
                        .id(studentId)
                        .personalInfo(Individual.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .image(image)
                                .reference(reference)
                                .build())
                        .build())
                .assignment(AssignmentDTO.builder()
                        .classe(ClasseDTO.builder()
                                .id(classeId)
                                .name(classeName)
                                .grade(GradeDTO.builder()
                                        .id(gradeId)
                                        .section(section)
                                        .subSection(subSection)
                                        .build())
                                .build())
                        .build())
                .obtainedMark(weightedAverage())
                .build();
    }
}
