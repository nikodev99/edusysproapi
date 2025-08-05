package com.edusyspro.api.repository;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    @Query("select s from Semester s where s.academicYear.school.id = ?1 order by s.template.displayOrder")
    List<Semester> getAllBySchoolId(UUID schoolId);

    @Query("SELECT s FROM Semester s WHERE s.template.semesterName = ?1")
    Optional<Semester> findSemesterBySemesterName(String semesterName);

    Optional<List<Semester>> findSemesterByAcademicYearId(UUID academicYearId);

    Optional<Semester> findSemesterBySemesterId(int semesterId);

    @Modifying
    @Transactional
    @Query("UPDATE Semester s SET s.academicYear = ?1 WHERE s.semesterId = ?2")
    int updateSemesterBySemesterId(AcademicYear academicYearId, int semesterId);
}
