package com.edusyspro.api.dto;

import com.edusyspro.api.model.CourseProgram;
import com.edusyspro.api.model.Semester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgramDTO {
    private Long id;
    private String topic;
    private String purpose;
    private String description;
    private Boolean active;
    private Boolean passed;
    private LocalDate updateDate;
    private Semester semester;
    private CourseDTO course;
    private ClasseDTO classe;
    private TeacherDTO teacher;

    public static CourseProgramDTO fromEntity(CourseProgram entity) {
        return CourseProgramDTO.builder()
                .id(entity.getId())
                .topic(entity.getTopic())
                .purpose(entity.getPurpose())
                .description(entity.getDescription())
                .active(entity.isActive())
                .passed(entity.isPassed())
                .updateDate(entity.getUpdateDate())
                .semester(entity.getSemester())
                .course(CourseDTO.fromEntity(entity.getCourse()))
                .classe(ClasseDTO.fromEntity(entity.getClasse()))
                .teacher(TeacherDTO.fromEntity(entity.getTeacher()))
                .build();
    }

    public static CourseProgram toEntity(CourseProgramDTO dto) {
        return CourseProgram.builder()
                .id(dto.getId())
                .topic(dto.getTopic())
                .purpose(dto.getPurpose())
                .description(dto.getDescription())
                .active(dto.getActive())
                .passed(dto.getPassed())
                .updateDate(dto.getUpdateDate())
                .semester(dto.getSemester())
                .course(CourseDTO.toEntity(dto.getCourse()))
                .classe(ClasseDTO.toEntity(dto.getClasse()))
                .teacher(TeacherDTO.toEntity(dto.getTeacher()))
                .build();
    }
}
