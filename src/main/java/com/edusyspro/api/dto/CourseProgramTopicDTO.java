package com.edusyspro.api.dto;

import com.edusyspro.api.model.CourseProgramTiming;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseProgramTopicDTO {
    private Long id;
    private CourseProgramDTO courseProgram;
    private String title;
    private CourseProgramTimingDTO timing;
    private String description;
    private Short order;
    private CourseProgramTopicDTO parentTopic;
    private List<CourseProgramTopicDTO> childTopics;
    private ZonedDateTime createAt;
}
