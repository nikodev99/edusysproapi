package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseProgramResponse {
    private TeacherDTO teacher;
    private CourseDTO course;
    private ClasseDTO classe;
    private AcademicYearDTO academicYear;
    private List<CourseProgramSemester> semesters;
}
