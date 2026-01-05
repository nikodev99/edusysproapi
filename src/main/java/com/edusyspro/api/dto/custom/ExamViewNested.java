package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.CourseDTO;

import java.util.List;

public record ExamViewNested(
        CourseDTO subject,
        List<AssignmentDTO> assignments,
        Double subjectAverage
) {
}
