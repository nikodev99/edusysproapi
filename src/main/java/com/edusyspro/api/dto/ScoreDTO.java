package com.edusyspro.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreDTO {
    private long id;
    private AssignmentDTO assignment;
    private StudentDTO student;
    private long obtainedMark;
}
