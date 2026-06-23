package com.edusyspro.api.dto;

import com.edusyspro.api.model.TeachingReport;
import com.edusyspro.api.model.enums.ReportStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeachingReportDTO {
    private Long id;
    private CourseProgramDTO courseProgram;
    private CourseProgramTopicDTO courseProgramTopic;
    private TeacherDTO teacher;
    private ScheduleDTO schedule;
    private LocalDate sessionDate;
    private LocalTime sessionStartingTime;
    private LocalTime sessionEndingTime;
    private int duration_minutes;
    private Boolean isLateSubmission;
    private ReportStatus reportStatus;
    private String notes;
    private ZonedDateTime createdAt;

    public TeachingReportDTO toReport(Tuple tuple){
        return TeachingReportDTO.builder()
                .id(tuple.get(0, Long.class))
                .courseProgram(CourseProgramDTO.builder().name(tuple.get(1, String.class)).build())
                .courseProgramTopic(CourseProgramTopicDTO.builder().title(tuple.get(2, String.class)).build())
                .sessionDate(tuple.get(3, LocalDate.class))
                .duration_minutes(tuple.get(4, Integer.class))
                .reportStatus(tuple.get(5, ReportStatus.class))
                .isLateSubmission(tuple.get(6, Boolean.class))
                .notes(tuple.get(7, String.class))
                .createdAt(tuple.get(8, ZonedDateTime.class))
                .build();
    }

    public TeachingReport toEntity() {
        return TeachingReport.builder()
                .courseProgram(courseProgram.toMerge())
                .courseProgramTopic(courseProgramTopic.toDetach())
                .teacher(teacher.toMarge())
                .schedule(schedule != null ? schedule.toDetach() : null)
                .sessionDate(sessionDate)
                .sessionStartingTime(sessionStartingTime)
                .sessionEndingTime(sessionEndingTime)
                .duration_minutes(sessionStartingTime != null && sessionEndingTime != null ?
                        Math.toIntExact(Duration.between(sessionStartingTime, sessionEndingTime).toMinutes()) : 0)
                .isLateSubmission(isLateSubmission == null ? sessionDate.isAfter(LocalDate.now()) : isLateSubmission)
                .reportStatus(reportStatus == null ? ReportStatus.SUBMITTED : reportStatus)
                .notes(notes)
                .build();
    }
}
