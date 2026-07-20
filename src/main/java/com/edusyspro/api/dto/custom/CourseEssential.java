package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.dto.TeacherCourseDTO;
import com.edusyspro.api.model.DepartmentBoss;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.CourseType;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public record CourseEssential(
        Integer id,
        String course,
        CourseType courseType,
        String abbr,
        String discipline,
        Integer departmentId,
        String name,
        String code,
        String purpose,
        Long bossId,
        Boolean bossCurrent,
        String firstName,
        String lastName,
        LocalDate startPeriod,
        LocalDate endPeriod,
        ZonedDateTime addedDate
) {
    public CourseDTO toCourse(){
        return CourseDTO.builder()
                .id(id)
                .course(course)
                .courseType(courseType)
                .abbr(abbr)
                .discipline(discipline)
                .department(DepartmentDTO.builder()
                        .id(departmentId)
                        .name(name)
                        .code(code)
                        .purpose(purpose)
                        .boss(DepartmentBoss.builder()
                                .current(bossCurrent)
                                .d_boss(Individual.builder()
                                        .id(bossId)
                                        .firstName(firstName)
                                        .lastName(lastName)
                                        .build())
                                .startPeriod(startPeriod)
                                .endPeriod(endPeriod)
                                .build())
                        .build())
                .createdAt(addedDate)
                .build();
    }

    public TeacherCourseDTO toTeacherCourse(){
        return TeacherCourseDTO.builder()
                .course(toCourse())
                .build();
    }
}
