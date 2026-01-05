package com.edusyspro.api.dto.custom;

public record ExamStatistics(
        Integer totalAssignments,
        Double totalMarks,
        Double successRate,
        Double medianAverage
) {
}
