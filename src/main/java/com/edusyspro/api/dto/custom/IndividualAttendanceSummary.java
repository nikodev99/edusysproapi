package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.Individual;

import java.util.List;

public record IndividualAttendanceSummary(Individual individual, List<IndividualAttendanceCount> statusCount) {
}
