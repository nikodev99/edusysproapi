package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.edusyspro.api.model.Teacher;
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
public class Classe {
    private int id;
    private String name;
    private String category;
    private Grade grade;
    private List<Schedule> schedule;
    private int roomNumber;
    private ClasseTeacherBoss principalTeacher;
    private ClasseStudentBoss principalStudent;
    private Course principalCourse;
    private List<EnrollmentEntity> students;
    private List<Teacher> classTeacherCourses;
    private double monthCost;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
