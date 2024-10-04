package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record StudentEssential (
    UUID id,
    String firstName,
    String lastName,
    Gender gender,
    String emailId,
    LocalDate birthDate,
    String birthCity,
    String nationality,
    String dadName,
    String momName,
    String reference,
    String telephone,
    Address address,
    String image
) {
    public static Student toStudent(StudentEssential s) {
        return Student.builder()
                .id(s.id)
                .firstName(s.firstName)
                .lastName(s.lastName)
                .gender(s.gender)
                .emailId(s.emailId)
                .birthDate(s.birthDate)
                .birthCity(s.birthCity)
                .nationality(s.nationality)
                .dadName(s.dadName)
                .momName(s.momName)
                .reference(s.reference)
                .telephone(s.telephone)
                .address(s.address)
                .image(s.image)
                .build();
    }
}
