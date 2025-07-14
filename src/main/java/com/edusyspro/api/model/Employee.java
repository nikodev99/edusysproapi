package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private LocalDate hireDate;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = Datetime.brazzavilleDatetime();
        modifyAt = Datetime.brazzavilleDatetime();
        active = true;
    }
}
