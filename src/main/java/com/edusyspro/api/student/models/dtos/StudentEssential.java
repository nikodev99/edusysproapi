package com.edusyspro.api.student.models.dtos;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.entities.enums.Gender;
import com.edusyspro.api.student.entities.HealthCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEssential {
    private UUID id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String emailId;
    private LocalDate birthDate;
    private String birthCity;
    private String nationality;
    private String dadName;
    private String momName;
    private String reference;
    private String telephone;
    private Address address;
    private GuardianEssential guardian;
    private HealthCondition healthCondition;
    private String image;
    private String currentSchoolName;
    private String currentSchoolAbbr;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
