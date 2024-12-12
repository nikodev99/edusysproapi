package com.edusyspro.api.dto;

import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Punishment;
import com.edusyspro.api.model.enums.ReprimandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReprimandDTO {
    private Long id;
    private EnrollmentDTO student;
    private LocalDate reprimandDate;
    private ReprimandType type;
    private String description;
    private Individual issuedBy;
    private Punishment punishment;
}
