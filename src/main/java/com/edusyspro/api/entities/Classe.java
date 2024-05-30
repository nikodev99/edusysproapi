package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Classe {

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

    @OneToMany(mappedBy = "classe", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Schedule> schedule;

    private int roomNumber;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "principal_teacher_id", referencedColumnName = "id")
    private Teacher principalTeacher;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "principal_student_id", referencedColumnName = "id")
    private Student principalStudent;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "principal_course_id", referencedColumnName = "id")
    private Course principalCourse;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Enrollment> students;

    @ManyToMany(mappedBy = "classes", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Teacher> teachers;

    private double monthCost;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private LocalDateTime createdAt;

    private LocalDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        modifyAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Classe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                //", grade=" + grade +
                ", schedule=" + schedule +
                ", roomNumber=" + roomNumber +
                ", principalTeacher=" + principalTeacher +
                ", principalStudent=" + principalStudent +
                ", principalCourse=" + principalCourse +
                //", students=" + students +
                //", teachers=" + teachers +
                ", monthCost=" + monthCost +
                //", school=" + school +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
