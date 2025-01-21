package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClasseDTO {
    private int id;
    private String name;
    private String category;
    private GradeDTO grade;
    private List<ScheduleDTO> schedule;
    private int roomNumber;
    private TeacherBossDTO principalTeacher;
    private StudentBossDTO principalStudent;
    private CourseDTO principalCourse;
    private List<EnrollmentDTO> students;
    @JsonProperty("classeTeachers")
    private List<TeacherDTO> classeTeachers;
    private double monthCost;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static ClasseDTO fromEntity(ClasseEntity classe){
        ClasseDTO copiedClasse = ClasseDTO.builder().build();
        BeanUtils.copyProperties(classe, copiedClasse);
        return copiedClasse;
    }

    public static ClasseEntity toEntity(ClasseDTO classe){
        ClasseEntity copiedClasse = ClasseEntity.builder().build();
        BeanUtils.copyProperties(classe, copiedClasse);
        return copiedClasse;
    }
}
