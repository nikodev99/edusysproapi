package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.model.DepartmentBoss;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Teacher;

import java.time.LocalDate;
import java.util.UUID;

public record DepartmentEssential(
        int id,
        String name,
        String code,
        String purpose,
        UUID bossId,
        boolean bossCurrent,
        String firstName,
        String lastName,
        String image,
        LocalDate startPeriod,
        LocalDate endPeriod
) {
    public DepartmentDTO toDepartmentDTO() {
        return DepartmentDTO.builder()
                .id(id)
                .name(name)
                .code(code)
                .purpose(purpose)
                .boss(DepartmentBoss.builder()
                        .current(bossCurrent)
                        .d_boss(Teacher.builder()
                                .id(bossId)
                                .personalInfo(Individual.builder()
                                        .firstName(firstName)
                                        .lastName(lastName)
                                        .image(image)
                                        .build())
                                .build())
                        .startPeriod(startPeriod)
                        .endPeriod(endPeriod)
                        .build())
                .build();
    }
}
