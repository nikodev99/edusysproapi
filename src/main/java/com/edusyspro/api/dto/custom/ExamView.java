package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.StudentDTO;

import java.util.List;
import java.util.UUID;

public record ExamView(
        UUID studentId,
        StudentDTO student,
        List<AssignmentTypeAverage> typeAverages,
        Double totalAverage,
        Integer rank,
        List<ExamViewNested> nested
) {
}
