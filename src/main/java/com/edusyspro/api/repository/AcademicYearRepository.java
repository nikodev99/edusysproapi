package com.edusyspro.api.repository;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.School;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, UUID> {

    @Query("SELECT a FROM AcademicYear a WHERE a.school.id = ?1 and EXTRACT(YEAR FROM a.startDate) >= ?2 ")
    List<AcademicYear> findAllBeginningOfYear(UUID schoolId, int year);

    @Query("SELECT a FROM AcademicYear a WHERE a.school.id = ?1 and ?2 >= a.startDate and a.endDate <= ?2")
    AcademicYear findBeginningOfYear(UUID schoolId, LocalDate date);

    @Query("select a.startDate, a.endDate from AcademicYear a where a.school.id = ?1")
    Optional<Tuple> findBySchool(UUID schoolId);

    Optional<AcademicYear> findAcademicYearById(UUID id);

    AcademicYear findAcademicYearBySchoolIdAndCurrentIsTrue(UUID schoolId);

    List<AcademicYear> findAllBySchoolId(UUID schoolId);

    @Modifying
    @Transactional
    @Query("update AcademicYear a set a.startDate = ?1, a.endDate = ?2, a.school = ?3 where a.id = ?4")
    int updateAcademicYearById(LocalDate start, LocalDate end, School school, int id);
}
