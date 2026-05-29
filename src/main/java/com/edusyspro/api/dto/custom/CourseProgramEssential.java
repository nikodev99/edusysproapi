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
    String getProgramName();
    String getPurpose();
    String getProgramDescription();
    ProgramStatus getProgramStatus();
    Semester getSemester();
    Long getProgramTimingId();
    LocalDate getProgramStartDate();
    LocalDate getProgramEndDate();
    UUID getAcademicYearId();
    String getAcademicYear();
    ZonedDateTime getProgramUpdateDate();
    ZonedDateTime getProgramCompletedDate();
    String getCourseName();
    String getCourseAbbr();
    String getClasseName();
    Section getSection();
    Individual getTeacher();
}
