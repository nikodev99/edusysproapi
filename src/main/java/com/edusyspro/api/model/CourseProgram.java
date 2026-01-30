package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    @Column(length = 500)
    private String purpose;
    @Column(length = 1000)
    private String description;
    private Boolean active;
    private Boolean passed;
    private LocalDate updateDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id")
    @JsonIgnore
    private Semester semester;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIgnore
    private Course course;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", referencedColumnName = "id")
    @JsonIgnore
    private ClasseEntity classe;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @JsonIgnore
    private Teacher teacher;

    private ZonedDateTime createAt;

    @PrePersist
    public void preInsert() {
        createAt = Datetime.brazzavilleDatetime();
    }
}
