package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.enums.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreEssential {
    private long id;
    private long assignmentId;
    private String examName;
    private LocalDate examDate;
    private LocalTime examStartTime;
    private LocalTime examEndDate;
    private String examClasse;
    private Section section;
    private int subjectId;
    private String subjectName;
    private long obtainedMark;

    public static ScoreDTO toDTO(ScoreEssential e) {
        return ScoreDTO.builder()
                .id(e.id)
                .assignment(AssignmentDTO.builder()
                        .id(e.assignmentId)
                        .examName(e.examName)
                        .examDate(e.examDate)
                        .startTime(e.examStartTime)
                        .endTime(e.examEndDate)
                        .classe(ClasseDTO.builder()
                                .name(e.examClasse)
                                .grade(GradeDTO.builder()
                                        .section(e.section)
                                        .build())
                                .build())
                        .subject(CourseDTO.builder()
                                .id(e.subjectId)
                                .course(e.subjectName)
                                .build())
                        .build())
                .obtainedMark(e.obtainedMark)
                .build();
    }
}
