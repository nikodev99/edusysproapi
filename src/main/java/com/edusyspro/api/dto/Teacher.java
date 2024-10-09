package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
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
public class Teacher{
    private UUID id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private Status status;
    private LocalDate birthDate;
    private Gender gender;
    private Address address;
    private String emailId;
    private String telephone;
    private LocalDate hireDate;
    private List<ClasseEntity> aClasses;
    private List<Course> courses;
    private double salaryByHour;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}