package com.edusyspro.api.model;

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
@Table(name = "classe")
public class ClasseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100)
    private String category;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grade grade;

    @OneToMany(mappedBy = "classeEntity", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Schedule> schedule;

    private int roomNumber;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "principal_teacher_id", referencedColumnName = "id")
    @JsonIgnore
    private ClasseTeacherBoss principalTeacher;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "principal_student_id", referencedColumnName = "id")
    @JsonIgnore
    private ClasseStudentBoss principalStudent;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "principal_course_id", referencedColumnName = "id")
    @JsonIgnore
    private Course principalCourse;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EnrollmentEntity> students;

    @ManyToMany(mappedBy = "aClasses", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Teacher> teachers;

    private double monthCost;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        modifyAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = ZonedDateTime.now();
    }
}
