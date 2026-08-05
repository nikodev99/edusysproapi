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
        TeacherSchoolAffiliation schoolAffiliation = teacher.getSchoolAffiliations() != null
                ? teacher.getSchoolAffiliations().stream()
                    .findFirst()
                    .orElse(TeacherSchoolAffiliation.builder().build())
                : TeacherSchoolAffiliation.builder().build();

        return TeacherDTO.builder()
                .id(teacher.getId())
                .personalInfo(teacher.getPersonalInfo())
                .aClasses(schoolAffiliation.getAClasses() != null ? SchoolAffiliationDTO.toClasse(schoolAffiliation): List.of())
                .courses(schoolAffiliation.getCourses() != null ? SchoolAffiliationDTO.toCourse(schoolAffiliation): List.of())
                //.courseProgram(teacher.getCourseProgram().stream().map(CourseProgramDTO::fromEntity).toList())
                .school(schoolAffiliation.getSchool() != null ? schoolAffiliation.getSchool(): null)
                .status(schoolAffiliation.getStatus() != null ? schoolAffiliation.getStatus() : null)
                .contract(schoolAffiliation.getContract() != null ? SchoolAffiliationDTO.toContract(schoolAffiliation): null)
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
                                .contract(teacherDTO.getContract() != null ? teacherDTO.getContract().toEntity() : null)
                                .aClasses(teacherDTO.getAClasses() != null ? teacherDTO.getAClasses().stream().map(c -> c.toEntity()).toList() : null)
                                .courses(teacherDTO.getCourses() != null ? teacherDTO.getCourses().stream().map(TeacherCourseDTO::toEntity).toList() : null)
                                .school(teacherDTO.getSchool() != null ? teacherDTO.getSchool() : null)
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