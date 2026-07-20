package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.AffiliationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDTO {
    private UUID id;
    private Individual personalInfo;

    @JsonProperty("classes")
    private List<TeacherClasseDTO> aClasses;

    private List<TeacherCourseDTO> courses;

    private List<List<CourseProgramDTO>> courseProgram;
    private School school;
    private AffiliationStatus status;
    private EmployeeContractDTO contract;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static TeacherDTO fromEntity(Teacher teacher){
        TeacherSchoolAffiliation schoolAffiliation = teacher.getSchoolAffiliations().stream()
                .findFirst()
                .orElse(TeacherSchoolAffiliation.builder().build());

        return TeacherDTO.builder()
                .id(teacher.getId())
                .personalInfo(teacher.getPersonalInfo())
                .aClasses(SchoolAffiliationDTO.toClasse(schoolAffiliation))
                .courses(SchoolAffiliationDTO.toCourse(schoolAffiliation))
                //.courseProgram(teacher.getCourseProgram().stream().map(CourseProgramDTO::fromEntity).toList())
                .school(schoolAffiliation.getSchool())
                .status(schoolAffiliation.getStatus())
                .contract(SchoolAffiliationDTO.toContract(schoolAffiliation))
                .createdAt(teacher.getCreatedAt())
                .modifyAt(teacher.getModifyAt())
                .build();
    }

    public static Teacher toEntity(TeacherDTO teacherDTO){
        return Teacher.builder()
                .id(teacherDTO.getId())
                .personalInfo(teacherDTO.getPersonalInfo())
                .schoolAffiliations(List.of(
                        TeacherSchoolAffiliation.builder()
                                .contract(teacherDTO.getContract().toEntity())
                                .aClasses(teacherDTO.getAClasses().stream().map(c -> c.toEntity()).toList())
                                .courses(teacherDTO.getCourses() != null ? teacherDTO.getCourses().stream().map(TeacherCourseDTO::toEntity).toList() : null)
                                .school(teacherDTO.getSchool())
                                .build()
                        )
                )
                //.courseProgram(teacherDTO.getCourseProgram().stream().map(CourseProgramDTO::toEntity).toList())
                .createdAt(teacherDTO.getCreatedAt())
                .modifyAt(teacherDTO.getModifyAt())
                .build();
    }

    public Teacher toMarge() {
        return Teacher.builder()
                .id(id)
                .build();
    }
}