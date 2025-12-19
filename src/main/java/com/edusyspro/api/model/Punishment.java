package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.PunishmentStatus;
import com.edusyspro.api.model.enums.PunishmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Punishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isRequire;

    @Enumerated(EnumType.ORDINAL)
    private PunishmentType type;

    @Column(length = 2000)
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.ORDINAL)
    private PunishmentStatus status;
    private String executedBy;
    private Boolean appealed;

    @Column(length = 2000)
    private String appealedNote;
}
