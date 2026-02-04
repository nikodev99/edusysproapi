package com.edusyspro.api.finance.entities;

import com.edusyspro.api.finance.enums.AccountParents;
import com.edusyspro.api.finance.enums.AccountType;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.utils.Datetime;
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
@Table(name = "account_charts", schema = "finance")
public class AccountCharts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @Column(length = 20)
    private String accountCode;

    @Column(length = 200)
    private String accountName;

    private Integer accountClass;

    @Column(length = 20)
    @Enumerated
    private AccountParents parentAccountCode;

    @Enumerated
    private AccountType accountType;

    private Boolean isActive;
    private Boolean isSystemAccount;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "createdBy", referencedColumnName = "id")
    private Individual createdBy;
    private ZonedDateTime createdAt;

    @PrePersist
    public void preInsert() {
        this.createdAt = Datetime.brazzavilleDatetime();
    }
}
