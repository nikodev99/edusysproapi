package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
import com.edusyspro.api.model.StudentEntity;
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
    private String firstName;
    private String lastName;
    private String maidenName;
    private Status status;

    @JsonProperty("gender")
    private Gender genre;

    private String emailId;
    private String jobTitle;
    private String company;
    private String telephone;
    private String mobile;
    private Address address;

    @JsonProperty("students")
    private List<Student> studentEntity;

    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
