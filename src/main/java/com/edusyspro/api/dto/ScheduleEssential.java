package com.edusyspro.api.dto;

import com.edusyspro.api.model.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleEssential {
    private Long id;
    private String teacherFirstName;
    private String teacherLastName;
    private String courseName;
    private String courseAbbr;
    private String designation;
    private Day dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
