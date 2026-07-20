package com.edusyspro.api.dto;

import com.edusyspro.api.model.School;
import com.edusyspro.api.model.TeacherClasses;
import com.edusyspro.api.model.TeacherCourses;
import com.edusyspro.api.model.TeacherSchoolAffiliation;
import com.edusyspro.api.model.enums.AffiliationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchoolAffiliationDTO {
    private Long id;
    private TeacherDTO teacher;
    @JsonProperty("classes")
    private List<TeacherClasseDTO> aClasses;
    private List<TeacherCourseDTO> courses;
    private School school;
    private AffiliationStatus status;
    private EmployeeContractDTO contract;

    public TeacherSchoolAffiliation toEntity() {
        return TeacherSchoolAffiliation.builder()
                .id(id)
                .teacher(teacher.toMarge())
                .aClasses(aClasses.stream().map(t -> t.toEntity()).toList())
                .courses(courses.stream().map(TeacherCourseDTO::toEntity).toList())
                .school(school)
                .status(status)
                .contract(contract.toEntity())
                .build();
    }

    public static SchoolAffiliationDTO fromEntity(TeacherSchoolAffiliation school) {
        return SchoolAffiliationDTO.builder()
                .id(school.getId())
                .school(school.getSchool())
                .status(school.getStatus())
                .contract(EmployeeContractDTO.fromEntity(school.getContract()))
                .build();
    }

    public static List<TeacherClasseDTO> toClasse(TeacherSchoolAffiliation school) {
        return school.getAClasses().stream().map(TeacherClasseDTO::toDto).toList();
    }

    public static List<TeacherCourseDTO> toCourse(TeacherSchoolAffiliation school) {
        return  school.getCourses().stream().map(TeacherCourseDTO::toDto).toList();
    }

    public static EmployeeContractDTO toContract(TeacherSchoolAffiliation school) {
        return EmployeeContractDTO.fromEntity(school.getContract());
    }

    public TeacherSchoolAffiliation mergeEntity() {
        return TeacherSchoolAffiliation.builder()
                .id(id)
                .build();
    }
}
