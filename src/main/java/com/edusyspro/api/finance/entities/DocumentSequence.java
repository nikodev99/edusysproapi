package com.edusyspro.api.finance.entities;

import com.edusyspro.api.finance.enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "document_seq", schema = "finance", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_abbr", "code", "type", "year"})
})
public class DocumentSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private UUID schoolId;
    private String schoolAbbr;

    private String code;

    @Enumerated
    private DocumentType type;
    private Year year;

    @Column(name = "last_sequence", nullable = false)
    private Long lastSequence;

    @Column(name = "total_generated", nullable = false)
    private Long totalGenerated; // Total documents generated this year

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
        if (lastSequence == null) {
            lastSequence = 0L;
        }
        if (totalGenerated == null) {
            totalGenerated = 0L;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
