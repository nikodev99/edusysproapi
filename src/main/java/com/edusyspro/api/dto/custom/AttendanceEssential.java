package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;

public record AttendanceEssential (
        long id,
        LocalDate attendanceDate,
        AttendanceStatus attendanceStatus,
        String classeName,
        String category,
        Section section
) {

    public AttendanceDTO populate() {
        return AttendanceDTO.builder()
                .id(id)
                .attendanceDate(attendanceDate)
                .status(attendanceStatus)
                .classeEntity(ClasseDTO.builder()
                        .name(classeName)
                        .category(category)
                        .grade(GradeDTO.builder()
                                .section(section)
                                .build())
                        .build())
                .build();
    }

}
