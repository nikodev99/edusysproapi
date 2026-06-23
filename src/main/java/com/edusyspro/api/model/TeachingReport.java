package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.ReportStatus;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    private LocalDate sessionDate;
    private LocalTime sessionStartingTime;
    private LocalTime sessionEndingTime;
    private int duration_minutes;
    private boolean isLateSubmission;

    @Enumerated
    private ReportStatus reportStatus;

    @Column(length = 2000)
    private String notes;
    private ZonedDateTime createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = Datetime.brazzavilleDatetime();
    }
}
