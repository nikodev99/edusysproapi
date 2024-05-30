package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * While fetching the school, we need the grade without school and plannings.
 *
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(length = 50)
    private String abbr;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private LocalDate foundedDate;

    @Column(length = 50)
    private String accreditationCode;

    @Column(length = 100)
    private String accreditationNumber;

    @Column(length = 100)
    private String contactEmail;

    @Column(length = 50)
    private String phoneNumber;

    @Column(length = 50)
    private String websiteURL;

    @OneToMany(mappedBy = "school", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Grade> grades;

    private LocalDateTime createdAt;

    private LocalDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        modifyAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = LocalDateTime.now();
    }

}
