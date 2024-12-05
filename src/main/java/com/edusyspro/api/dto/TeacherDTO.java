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
public class TeacherDTO {
    private UUID id;
    private Individual personalInfo;
    private LocalDate hireDate;

    @JsonProperty("classes")
    private List<ClasseDTO> aClasses;

    private List<CourseDTO> courses;
    private double salaryByHour;
    private List<CourseProgramDTO> courseProgram;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static TeacherDTO fromEntity(com.edusyspro.api.model.Teacher teacher){
        TeacherDTO copiedTeacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(teacher, copiedTeacherDTO);
        return copiedTeacherDTO;
    }

    public static com.edusyspro.api.model.Teacher toEntity(TeacherDTO teacherDTO){
        com.edusyspro.api.model.Teacher copiedTeacher = com.edusyspro.api.model.Teacher.builder().build();
        BeanUtils.copyProperties(teacherDTO, copiedTeacher);
        return copiedTeacher;
    }
}