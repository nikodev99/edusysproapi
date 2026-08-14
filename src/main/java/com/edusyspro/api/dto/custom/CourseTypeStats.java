package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.CourseType;

import java.time.LocalDate;

public record CourseTypeStats(
        CourseType courseType,
        Double weightedMarkSum,
        Long coefficientSum,
        Long assignmentCount,
        LocalDate lastAssignmentDate
) {
    public double weightedAverage() {
        return coefficientSum == 0 ? 0 : weightedMarkSum / coefficientSum;
    }

    public RadarAxis toRadarAxis(final int max_reliability) {
        return new RadarAxis(
                courseType,
                Math.round(weightedAverage() * 100.0) / 100.0,
                assignmentCount,
                assignmentCount >= max_reliability
        );
    }
}
