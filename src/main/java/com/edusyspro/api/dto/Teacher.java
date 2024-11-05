package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
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
    private Individual personalInfo;
    private LocalDate hireDate;

    @JsonProperty("classes")
    private List<ClasseEntity> aClasses;

    private List<Course> courses;
    private double salaryByHour;
    private List<CourseProgram> courseProgram;
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