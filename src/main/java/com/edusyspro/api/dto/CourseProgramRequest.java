package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgramRequest {
    private Long id;
    private String name;
    private List<CourseProgramTopicDTO> topic;
    private String purpose;
    private String description;
    private CourseProgramTimingDTO timing;
    private Semester semester;
    private CourseDTO course;
    private ClasseDTO classe;
    private TeacherDTO teacher;
    private ZonedDateTime createAt;

    public CourseProgram toEntity() {
        return CourseProgram.builder()
                .id(id)
                .name(name)
                .topic(List.of())
                .purpose(purpose)
                .description(description)
                .timing(timing != null ? timing.toTiming() : null)
                .semester(semester)
                .course(course != null ? course.toMarge() : null)
                .classe(classe != null ? classe.toMergeEntity() : null)
                .teacher(teacher != null ? teacher.toMarge() : null)
                .createAt(createAt)
                .build();
    }
}
