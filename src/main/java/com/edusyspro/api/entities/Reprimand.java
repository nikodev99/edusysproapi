package com.edusyspro.api.entities;

import com.edusyspro.api.entities.enums.ReprimandType;
import com.edusyspro.api.student.entities.StudentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reprimand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE , CascadeType.REFRESH})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    private LocalDate reprimandDate;
    private ReprimandType type;
    private String description;
    private String issuedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "punishment_id", referencedColumnName = "id")
    private Punishment punishment;
}
