package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;

import java.time.ZonedDateTime;
import java.util.UUID;

public record GuardianEssential (
    UUID id,
    Long personalInfoId,
    String firstName,
    String lastName,
    Gender gender,
    Status status,
    String emailId,
    String telephone,
    String mobile,
    String reference,
    Long addressId,
    Integer addressNumber,
    String addressStreet,
    String addressSecondStreet,
    String addressCity,
    String addressCountry,
    String neighborhood,
    String borough,
    String zipCode,
    String jobTitle,
    String company,
    ZonedDateTime createdAt,
    ZonedDateTime modifyAt
){
    public static GuardianDTO populateGuardian(GuardianEssential g) {
        return GuardianDTO.builder()
                .id(g.id)
                .personalInfo(Individual.builder()
                        .id(g.personalInfoId)
                        .firstName(g.firstName)
                        .lastName(g.lastName)
                        .gender(g.gender)
                        .status(g.status)
                        .emailId(g.emailId)
                        .telephone(g.telephone)
                        .mobile(g.mobile)
                        .reference(g.reference)
                        .address(Address.builder()
                                .id(g.addressId)
                                .street(g.addressStreet)
                                .secondStreet(g.addressSecondStreet)
                                .city(g.addressCity)
                                .country(g.addressCountry)
                                .number(g.addressNumber)
                                .zipCode(g.zipCode)
                                .borough(g.borough)
                                .neighborhood(g.neighborhood)
                                .build())
                        .build())
                .jobTitle(g.jobTitle)
                .company(g.company)
                .createdAt(g.createdAt)
                .modifyAt(g.modifyAt)
                .build();
    }
}
