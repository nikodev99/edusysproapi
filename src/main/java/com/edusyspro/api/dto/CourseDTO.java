package com.edusyspro.api.dto;

import com.edusyspro.api.model.Course;
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
public class CourseDTO {
    private Integer id;
    private String course;
    private String abbr;
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
               .department(DepartmentDTO.toEntity(course.getDepartment()))
               .createdAt(course.getCreatedAt())
               .modifyAt(course.getModifyAt())
               .build();
    }
}
