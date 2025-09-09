package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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
@EqualsAndHashCode(exclude = {"grades"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(length = 50)
    private String abbr;

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
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

    @OneToMany(mappedBy = "school", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Grade> grades;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        modifyAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = ZonedDateTime.now();
    }

}
