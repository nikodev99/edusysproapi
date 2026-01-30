package com.edusyspro.api.finance.entities;

import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payroll_periods", schema = "finance")
public class PayrollPeriods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private String periodName;
    private String periodCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private ZonedDateTime openedDate;
    private ZonedDateTime approvedDate;
    private LocalDate paidDate;
    private ZonedDateTime closedDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Individual createdBy;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private Individual approvedBy;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "closed_by", referencedColumnName = "id")
    private Individual closedBy;

    private ZonedDateTime createdAt;

    @PrePersist
    public void preInsert() {
        this.createdAt = Datetime.brazzavilleDatetime();
    }
}
