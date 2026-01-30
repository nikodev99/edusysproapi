package com.edusyspro.api.model;

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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 500, name = "course_name")
    private String course;

    @Column(length = 50, unique = true)
    private String abbr;

    @Column(length = 50)
    private String discipline;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        modifyAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = ZonedDateTime.now();
    }

}
