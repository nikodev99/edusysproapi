package com.edusyspro.api.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResult {
    private UUID studentId;
    private Map<String, Double> subjectAverage;
    private Double totalAverage;
    private Integer rank;
}
