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
    private List<TeacherDTO> classTeacherCourses;
    private double monthCost;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static ClasseDTO fromEntity(ClasseEntity classe){
        ClasseDTO copiedClasse = ClasseDTO.builder().build();
        BeanUtils.copyProperties(classe, copiedClasse);
        return copiedClasse;
    }

    public static ClasseEntity toEntity(ClasseDTO classe){
       return ClasseEntity.builder()
               .id(classe.getId())
               .name(classe.getName())
               .category(classe.getCategory())
               .grade(GradeDTO.toEntity(classe.getGrade()))
               .schedule(classe.getSchedule().stream().map(ScheduleDTO::toEntity).toList())
               .roomNumber(classe.getRoomNumber())
               .principalTeacher(TeacherBossDTO.toEntity(classe.getPrincipalTeacher()))
               .principalStudent(StudentBossDTO.toEntity(classe.getPrincipalStudent()))
               .principalCourse(CourseDTO.toEntity(classe.getPrincipalCourse()))
               .students(classe.getStudents().stream().map(EnrollmentDTO::toEntity).toList())
               .classTeacherCourses(classe.getClassTeacherCourses().stream().map(TeacherDTO::toEntity).toList())
               .monthCost(classe.getMonthCost())
               .createdAt(classe.getCreatedAt())
               .modifyAt(classe.getModifyAt())
               .build();
    }
}
