package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "academic_year")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean current;

    @JsonProperty("academicYear")
    private String years;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    @JsonIgnore
    private School school;

    @PrePersist
    public void preUpdate() {
        if (years == null) {
            years = startDate.getYear() + " - " + endDate.getYear();
        }
        if (current == null) {
            current = true;
        }
    }
}
