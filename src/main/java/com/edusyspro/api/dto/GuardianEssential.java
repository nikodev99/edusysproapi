package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;

import java.time.ZonedDateTime;
import java.util.UUID;

public record GuardianEssential (
    UUID id,
    String firstName,
    String lastName,
    String maidenName,
    Status status,
    Gender genre,
    String emailId,
    String jobTitle,
    String company,
    String telephone,
    String mobile,
    Address address,
    ZonedDateTime createdAt,
    ZonedDateTime modifyAt
){
    public static Guardian populateGuardian(GuardianEssential g) {
        return Guardian.builder()
                .id(g.id)
                .personalInfo(Individual.builder()
                        .firstName(g.firstName)
                        .lastName(g.lastName)
                        .maidenName(g.maidenName)
                        .status(g.status)
                        .gender(g.genre)
                        .emailId(g.emailId)
                        .telephone(g.telephone)
                        .mobile(g.mobile)
                        .address(g.address)
                        .build()
                )
                .jobTitle(g.jobTitle)
                .company(g.company)
                .createdAt(g.createdAt)
                .modifyAt(g.modifyAt)
                .build();
    }
}
