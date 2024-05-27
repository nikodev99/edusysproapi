package com.edusyspro.api.entities;

import com.edusyspro.api.entities.enums.Section;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "section_name", length = 50)
    @Enumerated(EnumType.STRING)
    private Section section;

    @Column(name = "program", length = 50)
    private String subSection;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Planning> planning;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyAt;

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", section=" + section +
                ", subSection='" + subSection + '\'' +
                //", school=" + school +
                ", planning=" + planning +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
