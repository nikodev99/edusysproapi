package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.model.enums.Section;

import java.util.List;

public record ClasseRanking(
        Integer classeId,
        String classeName,
        Section section,
        List<ScoreDTO> bestStudentScores,
        List<ScoreDTO> poorStudentScores
) {
}
