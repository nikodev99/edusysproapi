package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.Day;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @JsonIgnore
    private ClasseEntity classeEntity;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @JsonIgnore
    private Teacher teacher;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(length = 50)
    private String designation;

    private Day dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                //", classe=" + classe +
                //", teacher=" + teacher +
                //", course=" + course +
                ", designation='" + designation + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                //", school=" + school +
                '}';
    }
}
