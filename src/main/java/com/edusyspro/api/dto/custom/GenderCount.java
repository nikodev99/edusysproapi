package com.edusyspro.api.dto.custom;

import java.util.List;

public record GenderCount(
        long total,
        double totalAverageAge,
        List<StudentCount> genders
) {
}
