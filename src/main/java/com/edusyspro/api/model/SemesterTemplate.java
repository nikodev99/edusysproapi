package com.edusyspro.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "semester_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String semesterName;
    private int displayOrder;
    @Lob
    private String description;
}
