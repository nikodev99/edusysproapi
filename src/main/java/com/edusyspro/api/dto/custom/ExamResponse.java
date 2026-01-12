package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.ExamDTO;

import java.util.List;

public record ExamResponse(
        ExamDTO exam,
        List<ExamView> examView,
        List<AssignmentDTO> assignments,
        ExamStatistics statistics
) {
}
