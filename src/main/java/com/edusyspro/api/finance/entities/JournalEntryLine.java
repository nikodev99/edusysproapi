package com.edusyspro.api.finance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "journal_entry_line", schema = "finance")
public class JournalEntryLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "journal_entry_id", referencedColumnName = "id")
    private JournalEntries journalEntry;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_chart_id", referencedColumnName = "id")
    private AccountCharts accountChart;

    @Column(length = 2000)
    private String description;

    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private int lineNumber;
}
