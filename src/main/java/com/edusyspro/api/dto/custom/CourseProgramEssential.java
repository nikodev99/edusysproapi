package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.model.enums.ProgramStatus;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public interface CourseProgramEssential {
    Long getProgramId();
    Long getTopicId();
    String getProgramName();
    String getTopicTitle();
    String getPurpose();
    String getProgramDescription();
    String getTopicDescription();
    Short getOrder();
    ProgramStatus getProgramStatus();
    ProgramStatus getTopicStatus();
    Semester getSemester();
    Long getProgramTimingId();
    Long getTopicTimingId();
    LocalDate getProgramStartDate();
    LocalDate getProgramEndDate();
    LocalDate getTopicStartDate();
    LocalDate getTopicEndDate();
    UUID getAcademicYearId();
    String getAcademicYear();
    ZonedDateTime getProgramUpdateDate();
    ZonedDateTime getProgramCompletedDate();
    ZonedDateTime getTopicUpdateDate();
    ZonedDateTime getTopicCompletedDate();
    String getCourseName();
    String getCourseAbbr();
    String getClasseName();
    Section getSection();
    Individual getTeacher();
}
