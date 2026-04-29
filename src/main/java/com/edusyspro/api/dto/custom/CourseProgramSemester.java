package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.CourseProgramDTO;
import com.edusyspro.api.model.Semester;

import java.util.List;

public record CourseProgramSemester(
        Semester semester,
        List<CourseProgramDTO> programs
) {
}
