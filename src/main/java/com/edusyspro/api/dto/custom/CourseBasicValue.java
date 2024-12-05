package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.CourseDTO;

public record CourseBasicValue(
        int id,
        String course,
        String abbr
) {
    public CourseDTO toCourse(){
        return CourseDTO.builder()
                .id(id)
                .course(course)
                .abbr(abbr)
                .build();
    }
}
