package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;

public interface AttendanceInfo {
    AttendanceStatus getStatus();
    LocalDate getAttendanceDate();
    Section getSection();
    Gender getGender();
}
