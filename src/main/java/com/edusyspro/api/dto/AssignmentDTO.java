package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDTO {
    private Long id;
    private Planning semester;
    private Exam exam;
    private Individual preparedBy;
    private ClasseDTO classe;
    private CourseDTO subject;
    private String examName;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean passed;
    private List<ScoreDTO> marks;
    private ZonedDateTime addedDate;
    private ZonedDateTime updatedDate;
}