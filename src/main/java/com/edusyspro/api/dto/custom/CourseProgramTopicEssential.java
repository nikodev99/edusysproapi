package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.ProgramStatus;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface CourseProgramTopicEssential {
    Long getTopicId();
    String getTopicTitle();
    String getTopicDescription();
    Short getOrder();
    ProgramStatus getTopicStatus();
    Long getTopicTimingId();
    LocalDate getTopicStartDate();
    LocalDate getTopicEndDate();
    ZonedDateTime getTopicUpdateDate();
    ZonedDateTime getTopicCompletedDate();
    Long getProgramId();
}
