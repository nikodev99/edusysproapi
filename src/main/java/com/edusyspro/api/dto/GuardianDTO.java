package com.edusyspro.api.dto;

import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
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
public class GuardianDTO {
    private UUID id;
    private Individual personalInfo;
    private String jobTitle;
    private String company;

    @JsonProperty("students")
    private List<StudentDTO> studentDTOS;

    private String linkToStudent;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

}
