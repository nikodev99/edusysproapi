package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
import com.edusyspro.api.utils.JpaConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table (
        name = "teacher",
        uniqueConstraints = {@UniqueConstraint(name = "email_unique", columnNames = "email")}
)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String maidenName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    private LocalDate birthDate;

    private String cityOfBirth;

    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "email")
    private String emailId;

    private String image;

    private String telephone;

    private LocalDate hireDate;

    @OneToMany(mappedBy = "teacher", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH
    }, fetch = FetchType.EAGER)
    private List<TeacherClassCourse> teacherClassCourses;

    private double salaryByHour;

    @Convert(converter = JpaConverter.class)
    private List<String> attachments;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
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
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", maidenName='" + maidenName + '\'' +
                ", status=" + status +
                ", birthDate=" + birthDate +
                ", cityOfBirth='" + cityOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                ", gender=" + gender +
                //", address=" + address +
                ", emailId='" + emailId + '\'' +
                ", image='" + image + '\'' +
                ", telephone='" + telephone + '\'' +
                ", hireDate=" + hireDate +
                ", teacherClassCourses=" + teacherClassCourses +
                ", salaryByHour=" + salaryByHour +
                ", attachments=" + attachments +
                //", school=" + school +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
