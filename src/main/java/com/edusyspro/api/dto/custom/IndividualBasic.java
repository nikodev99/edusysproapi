package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.Individual;

public record IndividualBasic(
        Long id,
        String firstName,
        String lastName,
        String image
) {
    public Individual toEntity() {
        return Individual.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .image(image)
                .build();
    }
}
