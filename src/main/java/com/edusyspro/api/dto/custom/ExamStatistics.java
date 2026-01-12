package com.edusyspro.api.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamStatistics {
    private Integer totalAssignments;
    private Double totalMarks;
    private Double successRate;
    private Double medianAverage;
}
