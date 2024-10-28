package com.edusyspro.api.dto;

import com.edusyspro.api.model.Course;

public record CourseBasicValue(
        int id,
        String course,
        String abbr
) {
    public Course toCourse(){
        return Course.builder()
                .id(id)
                .course(course)
                .abbr(abbr)
                .build();
    }
}
