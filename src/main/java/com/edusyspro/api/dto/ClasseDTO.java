package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
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
}
