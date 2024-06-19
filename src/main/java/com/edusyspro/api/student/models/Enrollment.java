package com.edusyspro.api.student.models;

import com.edusyspro.api.classes.Classe;
import com.edusyspro.api.entities.School;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {
    private long id;
    private String academicYear;

    @JsonProperty("student")
    private Student student;

    @JsonProperty("classe")
    private Classe classe;

    private ZonedDateTime enrollmentDate;
    private boolean isArchived;
    private School school;
}
