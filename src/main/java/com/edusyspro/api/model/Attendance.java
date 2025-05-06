package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "individual_id", referencedColumnName = "id")
    @JsonIgnore
    private Individual individual;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @JsonProperty("classe")
    private ClasseEntity classeEntity;

    private LocalDate attendanceDate;

    private AttendanceStatus status;

}
