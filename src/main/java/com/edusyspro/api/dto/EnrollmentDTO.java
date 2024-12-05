package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentDTO {
    private long id;
    private AcademicYear academicYear;

    @JsonProperty("student")
    private StudentDTO student;

    @JsonProperty("classe")
    private ClasseDTO classe;

    private ZonedDateTime enrollmentDate;
    private boolean isArchived;
}
