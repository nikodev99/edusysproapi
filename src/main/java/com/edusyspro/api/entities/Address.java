package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int number;

    private String street;

    private String secondStreet;

    private String neighborhood;

    private String borough;

    private String city;

    @Column(name = "code_postal")
    private String zipCode;

    @Column(length = 100)
    private String country;

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
