package com.edusyspro.api.dto;

import com.edusyspro.api.model.Individual;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guardian {
    private UUID id;
    private Individual personalInfo;
    private String jobTitle;
    private String company;

    @JsonProperty("students")
    private List<Student> students;

    private String linkToStudent;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

}
