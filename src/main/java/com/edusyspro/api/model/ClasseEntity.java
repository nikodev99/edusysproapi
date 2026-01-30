package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100)
    private String category;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grade grade;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @OneToMany(mappedBy = "classeEntity", cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private List<Schedule> schedule;

    private Integer roomNumber;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "principal_teacher_id", referencedColumnName = "id")
    @JsonIgnore
    private ClasseTeacherBoss principalTeacher;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "principal_student_id", referencedColumnName = "id")
    @JsonIgnore
    private ClasseStudentBoss principalStudent;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "principal_course_id", referencedColumnName = "id")
    @JsonIgnore
    private Course principalCourse;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EnrollmentEntity> students;

    @ManyToMany(mappedBy = "aClasses", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Teacher> classTeachers;

    @Column(precision = 10, scale = 2)
    private BigDecimal monthCost;

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
