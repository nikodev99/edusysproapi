package com.edusyspro.api.dto;

import com.edusyspro.api.model.ClasseStudentBoss;
import com.edusyspro.api.model.ClasseTeacherBoss;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private ClasseStudentBoss principalStudentEntity;
    private Course principalCourse;
    private List<Enrollment> students;
    private List<Teacher> teachers;
    private double monthCost;
    private School school;
    private LocalDateTime createdAt;
    private LocalDateTime modifyAt;
}
