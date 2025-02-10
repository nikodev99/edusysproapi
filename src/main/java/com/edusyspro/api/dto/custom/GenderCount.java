package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.Gender;

public record GenderCount(
        Gender gender,
        long count,
        int ageAverage
) {
}

