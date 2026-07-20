package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table (name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personal_info", referencedColumnName = "id")
    private Individual personalInfo;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<CourseProgram> courseProgram;

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<TeacherSchoolAffiliation> schoolAffiliations;

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
                ", personalInfo=" + personalInfo +
                //", aClasses=" + aClasses +
                //", courses=" + courses +
                //", courseProgram=" + courseProgram +
                //", school=" + school +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                '}';
    }
}
