package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.model.DepartmentBoss;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Teacher;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record CourseEssential(
        int id,
        String course,
        String abbr,
        int departmentId,
        String name,
        String code,
        String purpose,
        UUID bossId,
        boolean bossCurrent,
        String firstName,
        String lastName,
        LocalDate startPeriod,
        LocalDate endPeriod,
        ZonedDateTime addedDate
) {
    public CourseDTO toCourse(){
        return CourseDTO.builder()
                .id(id)
                .course(course)
                .abbr(abbr)
                .department(DepartmentDTO.builder()
                        .id(departmentId)
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
                                                .build())
                                        .build())
                                .startPeriod(startPeriod)
                                .endPeriod(endPeriod)
                                .build())
                        .build())
                .createdAt(addedDate)
                .build();
    }
}
