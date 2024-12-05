package com.edusyspro.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {
    private Integer id;
    private String course;
    private String abbr;
    private DepartmentDTO department;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
