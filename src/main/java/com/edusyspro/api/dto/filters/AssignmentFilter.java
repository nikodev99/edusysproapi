package com.edusyspro.api.dto.filters;

import java.util.UUID;

public record AssignmentFilter(
        UUID academicYearId,
        Integer gradeId,
        Integer semesterId,
        Integer classeId,
        Integer courseId,
        String search
) {
}
