package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Section;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class AttendanceStatusCount {
    private Map<AttendanceStatus, Long> statusCount;
    private Map<Section, Map<AttendanceStatus, Long>> sectionStatusCount;
    private Map<Gender, Map<AttendanceStatus, Long>> genderStatusCount;

    public AttendanceStatusCount(Map<AttendanceStatus, Long> statusCount) {
        this.statusCount = statusCount;
    }

    public AttendanceStatusCount(Map<AttendanceStatus, Long> statusCount, Map<Section, Map<AttendanceStatus, Long>> sectionStatusCount, Map<Gender, Map<AttendanceStatus, Long>> genderStatusCount) {
        this.statusCount = statusCount;
        this.sectionStatusCount = sectionStatusCount;
        this.genderStatusCount = genderStatusCount;
    }
}
