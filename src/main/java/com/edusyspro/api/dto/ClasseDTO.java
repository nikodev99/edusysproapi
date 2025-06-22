package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClasseDTO {
    private Integer id;
    private String name;
    private String category;
    private GradeDTO grade;
    private List<ScheduleDTO> schedule;
    private Integer roomNumber;
    private TeacherBossDTO principalTeacher;
    private StudentBossDTO principalStudent;
    private CourseDTO principalCourse;
    private List<EnrollmentDTO> students;
    @JsonProperty("classeTeachers")
    private List<TeacherDTO> classTeacherCourses;
    private Double monthCost;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static ClasseDTO fromEntity(ClasseEntity classe){
        return ClasseDTO.builder()
                .id(classe.getId())
                .name(classe.getName())
                .category(classe.getCategory())
                .grade(GradeDTO.fromEntity(classe.getGrade()))
                .schedule(classe.getSchedule().stream().map(ScheduleDTO::fromEntity).toList())
                .roomNumber(classe.getRoomNumber())
                .principalTeacher(TeacherBossDTO.fromEntity(classe.getPrincipalTeacher()))
                .principalStudent(StudentBossDTO.fromEntity(classe.getPrincipalStudent()))
                .principalCourse(CourseDTO.fromEntity(classe.getPrincipalCourse()))
                .students(classe.getStudents().stream().map(EnrollmentDTO::fromEntity).toList())
                .classTeacherCourses(classe.getClassTeacherCourses().stream().map(TeacherDTO::fromEntity).toList())
                .monthCost(classe.getMonthCost())
                .createdAt(classe.getCreatedAt())
                .modifyAt(classe.getModifyAt())
                .build();
    }

    public static ClasseEntity toEntity(ClasseDTO classe){
       return ClasseEntity.builder()
               .id(classe.getId())
               .name(classe.getName())
               .category(classe.getCategory())
               .grade(GradeDTO.toEntity(classe.getGrade()))
               .roomNumber(classe.getRoomNumber())
               .monthCost(classe.getMonthCost())
               .createdAt(classe.getCreatedAt())
               .modifyAt(classe.getModifyAt())
               .build();
    }

    public ClasseEntity toMergeEntity(){
        return ClasseEntity.builder()
                .id(id)
                .build();
    }
}
