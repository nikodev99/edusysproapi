package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_info", referencedColumnName = "id")
    private Individual personalInfo;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonProperty("enrollments")
    private List<EnrollmentEntity> enrollmentEntities;

    private String dadName;

    private String momName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "guardian_id", referencedColumnName = "id")
    private GuardianEntity guardian;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "health_condition", referencedColumnName = "id")
    private HealthCondition healthCondition;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL)
    private List<Score> marks;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = Datetime.brazzavilleDatetime();
        modifyAt = Datetime.brazzavilleDatetime();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = Datetime.systemDatetime();
    }
}
