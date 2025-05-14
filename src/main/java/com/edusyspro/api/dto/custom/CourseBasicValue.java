package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.CourseDTO;

public record CourseBasicValue(
        Integer id,
        String course,
        String abbr
) {
    public CourseDTO toCourse(){
        if (id == null) return null;
        return CourseDTO.builder()
                .id(id)
                .course(course)
                .abbr(abbr)
                .build();
    }
}
