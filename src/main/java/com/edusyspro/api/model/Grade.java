package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.Section;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "section_name", length = 50)
    @Enumerated(EnumType.STRING)
    private Section section;

    @Column(name = "program", length = 50)
    private String subSection;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Planning> planning;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    @JsonIgnore
    private School school;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        modifyAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", section=" + section +
                ", subSection='" + subSection + '\'' +
                //", school=" + school +
                //", planning=" + planning +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
