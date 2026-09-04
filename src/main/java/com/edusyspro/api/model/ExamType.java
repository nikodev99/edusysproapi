package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @ManyToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    @JsonIgnore
    private School school;

    @Transient
    private String schoolId;

    @PrePersist
    @PreUpdate
    public void beforeSave() {
        if (schoolId != null && !schoolId.isBlank()) {
            try {
                UUID uuid = UUID.fromString(schoolId);
                this.school = School.builder().id(uuid).build();
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Invalid schoolId format: " + schoolId, e);
            }
        }
    }

    @PostLoad
    public void afterLoad() {
        if (this.school != null && this.school.getId() != null) {
            this.schoolId = this.school.getId().toString();
        }
    }
}
