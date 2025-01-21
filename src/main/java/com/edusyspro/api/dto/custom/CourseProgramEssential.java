package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.model.enums.Section;

import java.time.LocalDate;

public record CourseProgramEssential(
        Long id,
        String topic,
        String purpose,
        String description,
        boolean active,
        boolean passed,
        LocalDate updateDate,
        String semestre,
        String academicYear,
        String courseName,
        String courseAbbr,
        String classeName,
        Section section,
        Individual teacher
) {
    public CourseProgramDTO toDTO() {
        return CourseProgramDTO.builder()
                .id(id)
                .topic(topic)
                .purpose(purpose)
                .description(description)
                .active(active)
                .passed(passed)
                .updateDate(updateDate)
                .semester(Semester.builder()
                        .semesterName(semestre)
                        .academicYear(AcademicYear.builder()
                                .years(academicYear)
                                .build())
                        .build())
                .course(CourseDTO.builder()
                        .course(courseName)
                        .abbr(courseAbbr)
                        .build())
                .classe(ClasseDTO.builder()
                        .name(classeName)
                        .grade(GradeDTO.builder()
                                .section(section)
                                .build())
                        .build())
                .teacher(TeacherDTO.builder()
                        .personalInfo(teacher)
                        .build())
                .build();
    }
}
