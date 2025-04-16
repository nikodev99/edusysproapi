package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.Gender;

public record StudentCount(
        Gender gender,
        long count,
        double ageAverage
) {
}
