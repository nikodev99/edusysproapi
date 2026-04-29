package com.edusyspro.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeachingReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "course_program_id", referencedColumnName = "id")
    private CourseProgram courseProgram;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "course_program_topic_id", referencedColumnName = "id")
    private CourseProgramTopic courseProgramTopic;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    private LocalDate sessionDate;
    private LocalTime sessionStartingTime;
    private LocalTime sessionEndingTime;
    private int duration_minutes;

    @Column(length = 2000)
    private String notes;
}
