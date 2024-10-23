package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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
    private String cityOfBirth;
    private String nationality;
    private Gender gender;
    private Address address;
    private String emailId;
    private String telephone;
    private LocalDate hireDate;
    private List<Course> courses;
    @JsonProperty("classes")
    private List<ClasseEntity> aClasses;
    private double salaryByHour;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static Teacher fromEntity(com.edusyspro.api.model.Teacher teacher){
        Teacher copiedTeacher = new Teacher();
        BeanUtils.copyProperties(teacher, copiedTeacher);
        return copiedTeacher;
    }

    public static com.edusyspro.api.model.Teacher toEntity(Teacher teacher){
        com.edusyspro.api.model.Teacher copiedTeacher = com.edusyspro.api.model.Teacher.builder().build();
        BeanUtils.copyProperties(teacher, copiedTeacher);
        return copiedTeacher;
    }
}