package com.edusyspro.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_info", referencedColumnName = "id")
    private Individual personalInfo;

    private String jobTitle;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    private String contractType;

    private boolean active;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;
}
