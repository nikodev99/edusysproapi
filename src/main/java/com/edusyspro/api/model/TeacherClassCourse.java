package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "teacher_class_course", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"teacher_id", "classe_id", "course_id"})
})
public class TeacherClassCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @JsonIgnore
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classe_id", referencedColumnName = "id")
    private ClasseEntity classe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Override
    public String toString() {
        return "TeacherClassCourse{" +
                "id=" + id +
                //", teacher=" + teacher +
                ", classe=" + classe +
                ", course=" + course +
                '}';
    }
}
