package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.TeacherBossDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.model.Individual;

import java.time.LocalDate;
import java.util.UUID;

public record TeacherBossEssential(
        int id,
        UUID teacherId,
        String lastName,
        String firstName,
        boolean current,
        LocalDate startPeriod,
        LocalDate endPeriod
) {
    public TeacherBossDTO toDTO() {
        return TeacherBossDTO.builder()
                .id(id)
                .principalTeacher(TeacherDTO.builder()
                        .id(teacherId)
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
