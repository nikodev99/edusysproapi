package com.edusyspro.api.finance.entities;

import com.edusyspro.api.model.Address;
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
@Table(name = "vendors", schema = "finance")
public class Vendors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @Column(length = 200)
    private String vendor_name;

    @Column(length = 20)
    private String vendor_code;

    @Column(length = 50)
    private String contact;
    private String email;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private String tax;
    private String bankAccount;

    @Column(length = 100)
    private String paymentTerm;

    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "createdBy", referencedColumnName = "id")
    private Individual createdBy;

    private ZonedDateTime createdAt;

    @PrePersist
    public void preInsert() {
        this.createdAt = Datetime.brazzavilleDatetime();
    }
}
