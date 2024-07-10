package com.edusyspro.api.student.models.dtos;

import com.edusyspro.api.entities.dtos.PlanningEssential;
import com.edusyspro.api.entities.dtos.ScheduleEssential;
import com.edusyspro.api.entities.enums.Section;
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
public class StudentEnrollment {
    private long id;
    private String academicYear;
    private int classId;
    private String className;
    private String classCategory;
    private int gradeId;
    private Section gradeSection;
    private String gradeSubSection;
    private List<PlanningEssential> gradePlannings;
    private List<ScheduleEssential> classSchedule;
    private String classPrincipalTeacherFirstName;
    private String classPrincipalTeacherLastName;
    private String classPrincipalStudentFirstName;
    private String classPrincipalStudentLastName;
    private double classMonthCost;
    private String schoolName;
    private String schoolAbbr;
    private ZonedDateTime enrollmentDate;
    private boolean isArchived;
}
