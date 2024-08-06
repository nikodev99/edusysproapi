package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "guardian")
public class GuardianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String maidenName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @JsonProperty("gender")
    private Gender genre;

    @Column(name = "email", unique = true, length = 100)
    private String emailId;

    private String jobTitle;

    private String company;

    @Column(length = 50)
    private String telephone;

    @Column(length = 50)
    private String mobile;

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "guardian", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<StudentEntity> students;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = Datetime.systemDatetime();
        modifyAt = Datetime.systemDatetime();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = Datetime.systemDatetime();
    }

    @Override
    public String toString() {
        return "Guardian{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", maidenName='" + maidenName + '\'' +
                ", status=" + status +
                ", genre=" + genre +
                ", emailId='" + emailId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address=" + address +
                //", student=" + student +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
