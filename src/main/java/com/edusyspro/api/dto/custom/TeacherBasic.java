package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.model.Individual;

import java.util.List;
import java.util.UUID;

public record TeacherBasic(
        UUID teacherId,
        long individualId,
        String firstName,
        String lastName,
        String image,
        Integer classeId,
        String classeName
) {
    public TeacherDTO toDTO() {
        return TeacherDTO.builder()
                .id(teacherId)
                .personalInfo(Individual.builder()
                        .id(individualId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .image(image)
                        .build())
                .aClasses(List.of(ClasseDTO.builder()
                                .id(classeId)
                                .name(classeName)
                        .build()))
                .build();
    }
}
