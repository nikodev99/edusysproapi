package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TeacherBasic(
        UUID teacherId,
        long individualId,
        String firstName,
        String lastName,
        String image,
        Gender gender,
        String email,
        LocalDate birthDate,
        String nationality,
        String birthCity,
        String telephone,
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
                        .gender(gender)
                        .emailId(email)
                        .birthDate(birthDate)
                        .nationality(nationality)
                        .birthCity(birthCity)
                        .telephone(telephone)
                        .build())
                .aClasses(List.of(ClasseDTO.builder()
                                .id(classeId)
                                .name(classeName)
                        .build()))
                .build();
    }
}
