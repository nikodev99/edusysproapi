package com.edusyspro.api.classes;

import com.edusyspro.api.entities.*;
import com.edusyspro.api.student.models.Enrollment;
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
