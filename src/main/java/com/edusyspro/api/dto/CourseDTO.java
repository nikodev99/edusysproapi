package com.edusyspro.api.dto;

import com.edusyspro.api.model.Course;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
    private Integer id;
    private String course;
    private String abbr;
    private String discipline;
    private DepartmentDTO department;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static CourseDTO fromEntity(Course course){
        CourseDTO copiedCourse = CourseDTO.builder().build();
        BeanUtils.copyProperties(course, copiedCourse);
        return copiedCourse;
    }

    public static Course toEntity(CourseDTO course){
       return Course.builder()
               .id(course.getId())
               .course(course.getCourse())
               .abbr(course.getAbbr())
               .discipline(course.getDiscipline())
               .department(DepartmentDTO.toEntity(course.getDepartment()))
               .createdAt(course.getCreatedAt())
               .modifyAt(course.getModifyAt())
               .build();
    }
}
