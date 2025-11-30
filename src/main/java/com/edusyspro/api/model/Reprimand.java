package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.ReprimandType;
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

    @ManyToOne(cascade = {CascadeType.MERGE , CascadeType.DETACH})
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = {CascadeType.MERGE , CascadeType.DETACH})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    @ManyToOne(cascade = {CascadeType.MERGE , CascadeType.DETACH})
    @JoinColumn(name = "classe_id", referencedColumnName = "id")
    private ClasseEntity classe;

    private LocalDate reprimandDate;
    private ReprimandType type;
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE , CascadeType.DETACH})
    @JoinColumn(name = "issuer_id", referencedColumnName = "id")
    private Individual issuedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "punishment_id", referencedColumnName = "id")
    private Punishment punishment;
}
