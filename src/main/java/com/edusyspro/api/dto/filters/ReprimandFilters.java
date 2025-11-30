package com.edusyspro.api.dto.filters;

import com.edusyspro.api.model.enums.PunishmentStatus;
import com.edusyspro.api.model.enums.PunishmentType;
import com.edusyspro.api.model.enums.ReprimandType;

import java.time.LocalDate;
import java.util.UUID;

public record ReprimandFilters(
        UUID studentId,
        UUID academicYearId,
        Integer classeId,
        PunishmentType punishmentType,
        ReprimandType reprimandType,
        PunishmentStatus punishmentStatus,
        LocalDate[] reprimandBetween
) {
}
