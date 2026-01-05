package com.edusyspro.api.finance.repos;

import com.edusyspro.api.model.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @Column(length = 100)
    private String name;

    @Column(length = 20)
    private String category_code;

    @Column(length = 2000)
    private String description;
    private Boolean isMandatory;
    private Boolean isActive;

    @Column(length = 20)
    private String accountCode;
}
