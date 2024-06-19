package com.edusyspro.api.student.entities;

import com.edusyspro.api.entities.*;
import com.edusyspro.api.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private String emailId;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EnrollmentEntity> enrollmentEntities;

    private LocalDate birthDate;

    @Column(length = 100)
    private String birthCity;

    @Column(length = 100)
    private String nationality;

    private String dadName;

    private String momName;

    @Column(length = 50)
    private String reference;

    @Column(length = 50)
    private String telephone;

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "guardian_id", referencedColumnName = "id")
    private GuardianEntity guardian;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "health_condition", referencedColumnName = "id")
    private HealthCondition healthCondition;

    @Column(length = 100)
    private String image;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL)
    private List<Score> marks;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
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
}
