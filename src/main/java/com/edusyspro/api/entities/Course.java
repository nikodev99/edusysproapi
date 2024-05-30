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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 500, name = "course_name")
    private String course;

    @Column(length = 50, unique = true)
    private String abbr;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
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

}
