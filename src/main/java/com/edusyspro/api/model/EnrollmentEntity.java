package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "enrollment")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @JsonIgnore
    private StudentEntity student;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClasseEntity classe;

    private ZonedDateTime enrollmentDate;

    private Boolean isArchived;

    @PrePersist
    public void onCreate() {
        isArchived = false;
        enrollmentDate = Datetime.brazzavilleDatetime();
    }
}
