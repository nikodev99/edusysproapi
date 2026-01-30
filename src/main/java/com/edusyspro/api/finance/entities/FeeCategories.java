package com.edusyspro.api.finance.entities;

import com.edusyspro.api.model.School;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "fee_categories", schema = "finance")
public class FeeCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 20)
    private String category_code;

    @Column(length = 2000)
    private String description;
    private Boolean isMandatory;
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "account_chart", referencedColumnName = "id")
    private AccountCharts accountCode;
}
