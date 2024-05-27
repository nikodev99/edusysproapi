package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Classe classe;

    private String examName;

    private LocalDate examDate;

    private LocalTime startTime;

    private LocalTime endDate;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Score> marks;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

}
