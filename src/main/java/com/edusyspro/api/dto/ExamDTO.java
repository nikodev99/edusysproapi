package com.edusyspro.api.dto;

import com.edusyspro.api.model.Exam;
import com.edusyspro.api.model.ExamType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamDTO {
    long id;
    ExamType examType;
    List<AssignmentDTO> assignments;
    LocalDate startDate;
    LocalDate endDate;

    public Exam toEntity() {
        return Exam.builder()
                .id(id)
                .examType(examType)
                .assignments(assignments != null ?
                        assignments.stream().map(AssignmentDTO::toEntity).toList():
                        List.of()
                )
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
