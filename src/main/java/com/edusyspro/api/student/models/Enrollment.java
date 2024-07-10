package com.edusyspro.api.student.models;

import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.student.entities.StudentEntity;
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
    private StudentEntity student;

    @JsonProperty("classe")
    private ClasseEntity classe;

    private ZonedDateTime enrollmentDate;
    private boolean isArchived;
    private School school;
}
