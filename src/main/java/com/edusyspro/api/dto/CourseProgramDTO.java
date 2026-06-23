package com.edusyspro.api.dto;

import com.edusyspro.api.model.CourseProgram;
import com.edusyspro.api.model.Semester;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseProgramDTO {
    private Long id;
    private String name;
    private String purpose;
    private String description;
    private CourseProgramTimingDTO timing;
    private List<CourseProgramTopicDTO> topic;
    private String classeName;

    public CourseProgram toMerge() {
        return CourseProgram.builder()
                .id(id)
                .build();
    }
}
