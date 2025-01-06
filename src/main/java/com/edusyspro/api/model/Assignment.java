package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "planning_id", referencedColumnName = "id")
    @JsonIgnore
    private Planning semester;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private Exam exam;

    //Ceci sera toujours l'ID de l'enseignant
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "prepardeBy_id", referencedColumnName = "id")
    private Individual preparedBy;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @JsonProperty("classe")
    @JsonIgnore
    private ClasseEntity classeEntity;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course subject;

    private String examName;

    private LocalDate examDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean passed;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Score> marks;

    private ZonedDateTime addedDate;

    private ZonedDateTime updatedDate;

    @PrePersist
    public void prePersistAddedDate() {
        addedDate = Datetime.brazzavilleDatetime();
        updatedDate = Datetime.brazzavilleDatetime();
    }

    @PreUpdate
    public void preUpdateUpdatedDate() {
        updatedDate = Datetime.brazzavilleDatetime();
    }
}
