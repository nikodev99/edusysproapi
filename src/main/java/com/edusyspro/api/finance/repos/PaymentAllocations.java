package com.edusyspro.api.finance.repos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment_allocations", schema = "finance")
public class PaymentAllocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payments payment;

    private BigDecimal allocatedAmount;
    private ZonedDateTime allocationDate;
    private  ZonedDateTime createdAt;
}
