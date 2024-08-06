package com.edusyspro.api.dto;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.StudentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {
    private long id;
    private AcademicYear academicYear;

    @JsonProperty("student")
    private StudentEntity student;

    @JsonProperty("classe")
    private ClasseEntity classe;

    private ZonedDateTime enrollmentDate;
    private boolean isArchived;
}
