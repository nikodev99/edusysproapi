package com.edusyspro.api.dto;

import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Reprimand;
import com.edusyspro.api.model.enums.ReprimandType;
import com.edusyspro.api.service.impl.EnrollmentArchiver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReprimandDTO {
    private Long id;
    private EnrollmentDTO student;
    private ZonedDateTime reprimandDate;
    private ReprimandType type;
    private String description;
    private Individual issuedBy;
    private PunishmentDTO punishment;

    public Reprimand toReprimand() {
        return Reprimand.builder()
                .student(student.toMerge())
                .reprimandDate(reprimandDate)
                .type(type)
                .description(description)
                .issuedBy(Individual.builder()
                        .id(issuedBy.getId())
                        .build())
                .punishment(punishment.toPunishment())
                .build();
    }
}
