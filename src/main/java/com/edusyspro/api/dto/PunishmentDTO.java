package com.edusyspro.api.dto;

import com.edusyspro.api.model.Punishment;
import com.edusyspro.api.model.enums.PunishmentStatus;
import com.edusyspro.api.model.enums.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PunishmentDTO {
    private Long id;
    private Boolean isRequire;
    private PunishmentType type;
    private String description;

    private DateRange dateRange;
    private LocalDate startDate;
    private LocalDate endDate;

    private PunishmentStatus status;
    private String executedBy;
    private Boolean appealed;
    private String appealedNote;

    public Punishment toPunishment() {
        return Punishment.builder()
                .id(id)
                .isRequire(isRequire)
                .type(type)
                .description(description)
                .startDate(dateRange != null ? dateRange.startDate() : startDate)
                .endDate(dateRange != null ? dateRange.endDate() : endDate)
                .status(status)
                .appealed(appealed)
                .executedBy(executedBy)
                .appealedNote(appealedNote)
                .build();
    }

    public static PunishmentDTO toDto(Punishment punishment) {
        return PunishmentDTO.builder()
                .id(punishment.getId())
                .isRequire(punishment.getIsRequire())
                .type(punishment.getType())
                .description(punishment.getDescription())
                .startDate(punishment.getStartDate())
                .endDate(punishment.getEndDate())
                .status(punishment.getStatus())
                .appealed(punishment.getAppealed())
                .executedBy(punishment.getExecutedBy())
                .appealedNote(punishment.getAppealedNote())
                .build();
    }
}
