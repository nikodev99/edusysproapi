package com.edusyspro.api.finance.entities;

import com.edusyspro.api.finance.enums.JournalEntryType;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "journal_entries", schema = "finance")
public class JournalEntries {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @Column(length = 50)
    private String entryNumber;
    private ZonedDateTime entryDate;

    @Enumerated
    private JournalEntryType journalEntryType;

    @Column(length = 50)
    private String referenceType;

    private UUID referenceId;

    @Column(length = 5000)
    private String description;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private Boolean isPosted;
    private LocalDate postedDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "createdBy", referencedColumnName = "id")
    private Individual createdBy;
    private ZonedDateTime createdAt;

    @PrePersist
    public void preInsert() {
        this.createdAt = Datetime.brazzavilleDatetime();
    }
}
