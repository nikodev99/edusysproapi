package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.dto.TeacherCourseDTO;
import com.edusyspro.api.model.enums.CourseType;

public record CourseBasicValue(
        Integer id,
        String course,
        CourseType courseType,
        String abbr,
        String discipline
) {
    public CourseDTO toCourse(){
        if (id == null) return null;
        return CourseDTO.builder()
                .id(id)
                .course(course)
                .courseType(courseType)
                .abbr(abbr)
                .discipline(discipline)
                .build();
    }

    public TeacherCourseDTO toTeacherCourse(){
        return TeacherCourseDTO.builder()
                .course(toCourse())
                .build();
    }
}
