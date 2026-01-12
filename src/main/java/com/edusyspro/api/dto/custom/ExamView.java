package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamView {
    private UUID studentId;
    private StudentDTO student;
    private List<AssignmentTypeAverage> typeAverages;
    private Double totalAverage;
    private Integer rank;
    private List<ExamViewNested> nested;
}
