package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "courseProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseProgramTopic> topic;

    @Column(length = 1000)
    private String purpose;

    @Column(length = 2000)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private CourseProgramTiming timing;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id")
    @JsonIgnore
    private Semester semester;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIgnore
    private Course course;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", referencedColumnName = "id")
    @JsonIgnore
    private ClasseEntity classe;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @JsonIgnore
    private Teacher teacher;

    private ZonedDateTime createAt;

    @PrePersist
    public void preInsert() {
        createAt = Datetime.brazzavilleDatetime();
    }
}
