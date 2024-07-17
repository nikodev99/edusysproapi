package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "academic_year")
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean current;

    private String years;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @PrePersist
    public void preUpdate() {
        Year startYear = Year.of(startDate.getYear());
        Year endYear = Year.of(endDate.getYear());
        years = startYear + " - " + endYear;
    }
}
