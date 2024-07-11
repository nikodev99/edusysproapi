package com.edusyspro.api.student.models.dtos;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.entities.enums.Gender;
import com.edusyspro.api.entities.enums.Status;
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
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianMaidenName;
    private Status guardianStatus;
    private Gender guardianGenre;
    private String guardianEmailId;
    private String guardianJobTitle;
    private String guardianCompany;
    private String guardianTelephone;
    private String guardianMobile;
    private Address guardianAddress;
    private HealthCondition healthCondition;
    private String image;
    private String currentSchoolName;
    private String currentSchoolAbbr;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
