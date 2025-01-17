package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
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
    private Grade grade;
    private List<ScheduleDTO> schedule;
    private int roomNumber;
    private ClasseTeacherBoss principalTeacher;
    private ClasseStudentBoss principalStudent;
    private CourseDTO principalCourse;
    private List<EnrollmentDTO> students;
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
        ClasseEntity copiedClasse = ClasseEntity.builder().build();
        BeanUtils.copyProperties(classe, copiedClasse);
        return copiedClasse;
    }
}
