package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.CourseType;

public record RadarAxis(
        CourseType courseType,
        double average,
        long assignmentCount,
        boolean reliable
) {
}
