package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgramTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = { CascadeType.DETACH })
    @JoinColumn(name = "course_program_id", referencedColumnName = "id")
    private CourseProgram courseProgram;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_status_id", referencedColumnName = "id")
    private CourseProgramTiming timing;

    private String description;

    @Column(name = "sort_order")
    private Short order;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "parent_topic_id", referencedColumnName = "id")
    @JsonBackReference
    private CourseProgramTopic parentTopic;

    @OneToMany(mappedBy = "parentTopic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CourseProgramTopic> childTopics;

    private ZonedDateTime createAt;

    @PrePersist
    public void preInsert() {
        createAt = Datetime.brazzavilleDatetime();
    }
}
