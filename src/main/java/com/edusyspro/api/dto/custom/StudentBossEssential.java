package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.StudentBossDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.Individual;

import java.time.LocalDate;
import java.util.UUID;

public record StudentBossEssential(
        int id,
        UUID studentId,
        String lastName,
        String firstName,
        boolean current,
        LocalDate startPeriod,
        LocalDate endPeriod
) {
   public StudentBossDTO toDTO() {
       return StudentBossDTO.builder()
               .id(id)
               .principalStudent(StudentDTO.builder()
                       .id(studentId)
                       .personalInfo(Individual.builder()
                               .firstName(firstName)
                               .lastName(lastName)
                               .build())
                       .build())
               .current(current)
               .startPeriod(startPeriod)
               .endPeriod(endPeriod)
               .build();
   }
}
