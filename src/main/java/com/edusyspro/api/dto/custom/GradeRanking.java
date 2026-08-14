package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.model.enums.Section;

import java.util.List;

public record GradeRanking(
        Integer gradeId,
        String classeName,
        Section section,
        String subSection,
        List<ScoreDTO> bestStudentScores,
        List<ScoreDTO> poorStudentScores
) {
}
