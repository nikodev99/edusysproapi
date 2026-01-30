package com.edusyspro.api.model;

import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 300)
    private String name;

    @Column(length = 50)
    private String code;

    @Column(length = 500)
    private String purpose;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "d_boss", referencedColumnName = "id")
    @JsonIgnore
    private DepartmentBoss boss;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grade grade;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    @JsonIgnore
    private School school;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        createdAt = Datetime.brazzavilleDatetime();
        modifyAt = Datetime.brazzavilleDatetime();
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = Datetime.brazzavilleDatetime();
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", boss=" + boss +
                //", school=" + school +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
