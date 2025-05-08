package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.AssignmentType;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignmentDTO {
    private Long id;
    private PlanningDTO semester;
    private ExamDTO exam;
    private Individual preparedBy;
    private ClasseDTO classe;
    private CourseDTO subject;
    private String examName;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean passed;
    private int coefficient;
    private List<ScoreDTO> marks;
    private AssignmentType type;
    private ZonedDateTime addedDate;
    private ZonedDateTime updatedDate;

    public Assignment toEntity() {
        return Assignment.builder()
                .id(id)
                .semester(PlanningDTO.toEntity(semester))
                .exam(exam.toEntity())
                .preparedBy(preparedBy)
                .classeEntity(ClasseDTO.toEntity(classe))
                .subject(CourseDTO.toEntity(subject))
                .examName(examName)
                .examDate(examDate)
                .startTime(startTime)
                .endTime(endTime)
                .passed(passed)
                .coefficient(coefficient)
                .type(type)
                .addedDate(addedDate)
                .updatedDate(updatedDate)
                .build();
    }
}
