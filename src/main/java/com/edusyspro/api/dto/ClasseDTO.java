package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private DepartmentDTO department;
    private List<ScheduleDTO> schedule;
    private Integer roomNumber;
    private TeacherBossDTO principalTeacher;
    private StudentBossDTO principalStudent;
    private CourseDTO principalCourse;
    private List<EnrollmentDTO> students;
    @JsonProperty("classeTeachers")
    private List<TeacherDTO> classTeacherCourses;
    private BigDecimal monthCost;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static ClasseDTO fromEntity(ClasseEntity classe){
        return ClasseDTO.builder()
                .id(classe.getId())
                .name(classe.getName())
                .category(classe.getCategory())
                .grade(
                        classe.getGrade() != null
                                ? GradeDTO.fromEntity(classe.getGrade())
                                : null
                )
                .department(
                        classe.getDepartment() != null
                                ? DepartmentDTO.fromEntity(classe.getDepartment())
                                : null
                )
                .schedule(
                        classe.getSchedule() != null
                                ? classe.getSchedule().stream().map(ScheduleDTO::fromEntity).toList()
                                : List.of()
                )
                .roomNumber(classe.getRoomNumber())
                .principalTeacher(
                        classe.getPrincipalTeacher() != null
                                ? TeacherBossDTO.fromEntity(classe.getPrincipalTeacher())
                                : null
                )
                .principalStudent(
                        classe.getPrincipalStudent() != null
                                ? StudentBossDTO.fromEntity(classe.getPrincipalStudent())
                                : null
                )
                .principalCourse(
                        classe.getPrincipalCourse() != null
                                ? CourseDTO.fromEntity(classe.getPrincipalCourse())
                                : null
                )
                .students(
                        classe.getStudents() != null
                                ? classe.getStudents().stream().map(EnrollmentDTO::fromEntity).toList()
                                : List.of()
                )
                .classTeacherCourses(
                        classe.getClassTeacherCourses() != null
                                ? classe.getClassTeacherCourses().stream().map(TeacherDTO::fromEntity).toList()
                                : List.of()
                )
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
               .grade(
                       classe.getGrade() != null
                               ? classe.getGrade().mergeGrade()
                               : null
               )
               .department(
                       classe.getDepartment() != null
                               ? DepartmentDTO.toEntity(classe.getDepartment())
                               : null
               )
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
