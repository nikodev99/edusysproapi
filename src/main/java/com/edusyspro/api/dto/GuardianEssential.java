package com.edusyspro.api.dto;

import com.edusyspro.api.model.Individual;

import java.time.ZonedDateTime;
import java.util.UUID;

public record GuardianEssential (
    UUID id,
    Individual personalInfo,
    String jobTitle,
    String company,
    ZonedDateTime createdAt,
    ZonedDateTime modifyAt
){
    public static Guardian populateGuardian(GuardianEssential g) {
        return Guardian.builder()
                .id(g.id)
                .personalInfo(g.personalInfo)
                .jobTitle(g.jobTitle)
                .company(g.company)
                .createdAt(g.createdAt)
                .modifyAt(g.modifyAt)
                .build();
    }
}
