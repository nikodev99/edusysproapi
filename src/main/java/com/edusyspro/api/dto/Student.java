package com.edusyspro.api.dto;

import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.Score;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private UUID id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String emailId;

    @JsonProperty("enrollments")
    private List<EnrollmentEntity> enrollmentEntities;

    private LocalDate birthDate;
    private String birthCity;
    private String nationality;
    private String dadName;
    private String momName;
    private String reference;
    private String telephone;
    private Address address;
    private GuardianEntity guardian;
    private HealthCondition healthCondition;
    private String image;
    private List<Score> marks;
    private List<Attendance> attendances;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
