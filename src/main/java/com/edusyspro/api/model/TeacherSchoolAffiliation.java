package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.AffiliationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherSchoolAffiliation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @OneToMany(mappedBy = "affiliation", cascade = {CascadeType.ALL})
    @JsonProperty("classes")
    private List<TeacherClasses> aClasses;

    @OneToMany(mappedBy = "affiliation", cascade = {CascadeType.ALL})
    private List<TeacherCourses> courses;

    @ManyToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @Enumerated(EnumType.STRING)
    private AffiliationStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_contract_id", referencedColumnName = "id")
    private EmployeeContracts contract;

    @PrePersist
    public void preInsert() {
        status = AffiliationStatus.ACTIVE;
    }
}
