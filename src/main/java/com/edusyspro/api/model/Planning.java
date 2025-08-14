package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id")
    @JsonProperty("semester")
    private Semester semestre;

    @Column(length = 500)
    private String designation;

    private LocalDate termStartDate;

    private LocalDate termEndDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    @JsonIgnore
    private Grade grade;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        return "{id=" + id +
                //", academicYear=" + academicYear +
                ", designation=" + designation +
                ", termStartDate=" + termStartDate +
                ", termEndDate=" + termEndDate +
                ", semestre=" + semestre +
                //", grade=" + grade +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt + "}";
    }
}
