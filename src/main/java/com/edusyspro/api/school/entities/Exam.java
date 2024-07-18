package com.edusyspro.api.school.entities;

import com.edusyspro.api.classes.ClasseEntity;
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

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "planning_id", referencedColumnName = "id")
    private Planning semester;

    private String preparedBy;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClasseEntity classeEntity;

    private String examName;

    private LocalDate examDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Score> marks;

}
