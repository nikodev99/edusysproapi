package com.edusyspro.api.dto;

import com.edusyspro.api.model.TeacherCourses;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherCourseDTO {
    private Long id;
    private CourseDTO course;
    private SchoolAffiliationDTO affiliation;

    public TeacherCourses toEntity() {
        return TeacherCourses.builder()
                .id(id)
                .course(course != null ? CourseDTO.toEntity(course) : null)
                .build();
    }

    public TeacherCourseDTO toDto() {
        return TeacherCourseDTO.builder()
                .id(id)
                .course(course != null ? course : null)
                .build();
    }

    public TeacherCourses toEntity(TeacherCourseDTO dto) {
        return TeacherCourses.builder()
                .id(dto.getId())
                .course(dto.getCourse() != null ? CourseDTO.toEntity(dto.getCourse()) : null)
                .build();
    }

    public static TeacherCourseDTO toDto(TeacherCourses entity) {
        return TeacherCourseDTO.builder()
                .id(entity.getId())
                .course(entity.getCourse() != null ? CourseDTO.fromEntity(entity.getCourse()) : null)
                .build();
    }
}
