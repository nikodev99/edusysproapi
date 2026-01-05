package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.AssignmentType;

public record AssignmentTypeAverage(
        AssignmentType type,
        Double average
) {
}
