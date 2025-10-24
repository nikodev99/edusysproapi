package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "semester_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"school"})
public class SemesterTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String semesterName;
    private int displayOrder;

    @Column(length = 2000)
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
