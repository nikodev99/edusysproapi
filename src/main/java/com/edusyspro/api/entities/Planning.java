package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    private String academicYear;

    @Column(length = 500)
    private String designation;

    private LocalDate termStartDate;

    private LocalDate termEndDate;

    @Column(length = 20)
    private String semestre;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grade grade;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                ", academicYear='" + academicYear + '\'' +
                ", designation='" + designation + '\'' +
                ", termStartDate=" + termStartDate +
                ", termEndDate=" + termEndDate +
                ", semestre='" + semestre + '\'' +
                //", school=" + school +
                //", grade=" + grade +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
