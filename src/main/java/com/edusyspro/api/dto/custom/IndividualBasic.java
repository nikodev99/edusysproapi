package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.Individual;

public record IndividualBasic(
        Long id,
        String firstName,
        String lastName,
        String image,
        String reference
) {
    public Individual toEntity() {
        return Individual.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .image(image)
                .reference(reference)
                .build();
    }

    public static IndividualBasic build(EnrollmentEntity e) {
        return new IndividualBasic(
                e.getId(),
                e.getStudent().getPersonalInfo().getFirstName(),
                e.getStudent().getPersonalInfo().getLastName(),
                e.getStudent().getPersonalInfo().getImage(),
                e.getStudent().getPersonalInfo().getReference()
        );
    }
}
