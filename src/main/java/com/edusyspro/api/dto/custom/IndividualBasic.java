package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.Individual;

public record IndividualBasic(
        String firstName,
        String lastName,
        String image
) {
    public Individual toEntity() {
        return Individual.builder()
                .firstName(firstName)
                .lastName(lastName)
                .image(image)
                .build();
    }
}
