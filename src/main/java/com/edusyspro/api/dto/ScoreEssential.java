package com.edusyspro.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreEssential {
    private long id;
    private String examName;
    private LocalDate examDate;
    private LocalTime examStartTime;
    private LocalTime examEndDate;
    private byte obtainedMark;
}
