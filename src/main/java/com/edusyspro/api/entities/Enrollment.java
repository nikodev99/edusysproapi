package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10)
    private String academicYear;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Classe classe;

    @Column(columnDefinition = "Timestamp")
    private LocalDateTime enrollmentDate;

    private boolean isArchived;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", academicYear='" + academicYear + '\'' +
                //", student=" + student +
                ", classe=" + classe +
                ", enrollmentDate=" + enrollmentDate +
                ", isArchived=" + isArchived +
                ", school=" + school +
                '}';
    }
}
