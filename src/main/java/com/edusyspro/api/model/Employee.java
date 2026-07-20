package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "personal_info", referencedColumnName = "id")
    private Individual personalInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private EmployeeContracts contract;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = Datetime.brazzavilleDatetime();
        modifyAt = Datetime.brazzavilleDatetime();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = Datetime.brazzavilleDatetime();
    }
}
