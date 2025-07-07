package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDTO {
    private UUID id;
    private Individual personalInfo;
    private LocalDate hireDate;

    @JsonProperty("classes")
    private List<ClasseDTO> aClasses;

    private List<CourseDTO> courses;
    private BigDecimal salaryByHour;
    private List<CourseProgramDTO> courseProgram;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static TeacherDTO fromEntity(Teacher teacher){
        return TeacherDTO.builder()
                .id(teacher.getId())
                .personalInfo(teacher.getPersonalInfo())
                .hireDate(teacher.getHireDate())
                .aClasses(teacher.getAClasses().stream().map(ClasseDTO::fromEntity).toList())
                .courses(teacher.getCourses().stream().map(CourseDTO::fromEntity).toList())
                .salaryByHour(teacher.getSalaryByHour())
                .courseProgram(teacher.getCourseProgram().stream().map(CourseProgramDTO::fromEntity).toList())
                .school(teacher.getSchool().get(0))
                .createdAt(teacher.getCreatedAt())
                .modifyAt(teacher.getModifyAt())
                .build();
    }

    public static Teacher toEntity(TeacherDTO teacherDTO){
        return Teacher.builder()
                .id(teacherDTO.getId())
                .personalInfo(teacherDTO.getPersonalInfo())
                .hireDate(teacherDTO.getHireDate())
                .aClasses(teacherDTO.getAClasses().stream().map(ClasseDTO::toEntity).toList())
                .courses(teacherDTO.getCourses().stream().map(CourseDTO::toEntity).toList())
                .salaryByHour(teacherDTO.getSalaryByHour())
                .courseProgram(teacherDTO.getCourseProgram().stream().map(CourseProgramDTO::toEntity).toList())
                .school(List.of(teacherDTO.getSchool()))
                .createdAt(teacherDTO.getCreatedAt())
                .modifyAt(teacherDTO.getModifyAt())
                .build();
    }
}