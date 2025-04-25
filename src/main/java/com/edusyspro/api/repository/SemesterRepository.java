package com.edusyspro.api.repository;

import com.edusyspro.api.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    @Query("select s from Semester s where s.academicYear.school.id = ?1")
    List<Semester> getAllBySchoolId(UUID schoolId);

}
