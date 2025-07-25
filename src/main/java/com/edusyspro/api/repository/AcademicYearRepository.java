package com.edusyspro.api.repository;

import com.edusyspro.api.dto.AcademicYearDTO;
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

    @Query("""
        SELECT new com.edusyspro.api.dto.AcademicYearDTO(a.id, a.startDate, a.endDate, a.current, a.years, null) 
        FROM AcademicYear a WHERE a.school.id = ?1 and EXTRACT(YEAR FROM a.startDate) >= ?2
    """)
    List<AcademicYearDTO> findAllBeginningOfYear(UUID schoolId, int year);

    @Query("""
        SELECT new com.edusyspro.api.dto.AcademicYearDTO(a.id, a.startDate, a.endDate, a.current, a.years, null)
        FROM AcademicYear a WHERE a.school.id = ?1 and ?2 >= a.startDate and a.endDate <= ?2
    """)
    AcademicYearDTO findBeginningOfYear(UUID schoolId, LocalDate date);

    @Query("select a.startDate, a.endDate from AcademicYear a where a.school.id = ?1")
    Optional<Tuple> findBySchool(UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.AcademicYearDTO(a.id, a.startDate, a.endDate, a.current, a.years, null)
        from AcademicYear a where a.id = ?1
    """)
    Optional<AcademicYearDTO> findAcademicYearById(UUID id);

    @Query("""
        select new com.edusyspro.api.dto.AcademicYearDTO(a.id, a.startDate, a.endDate, a.current, a.years, null)
        from AcademicYear a where a.school.id = ?1 and a.current = true
    """)
    AcademicYearDTO findAcademicYearBySchoolIdAndCurrentIsTrue(UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.AcademicYearDTO(a.id, a.startDate, a.endDate, a.current, a.years, null)
        from AcademicYear a where a.school.id = ?1
    """)
    List<AcademicYearDTO> findAllBySchoolId(UUID schoolId);

    @Modifying
    @Transactional
    @Query("update AcademicYear a set a.startDate = ?1, a.endDate = ?2, a.school = ?3 where a.id = ?4")
    int updateAcademicYearById(LocalDate start, LocalDate end, School school, int id);

    @Modifying
    @Transactional
    @Query("update AcademicYear a set a.current = false where a.id = ?1")
    int changeAcademicYearStatus(UUID academicYearId);

    @Query("SELECT COUNT(a.id) FROM AcademicYear a WHERE a.startDate = ?1 AND a.endDate = ?2 AND a.school.id = ?3")
    Long countAcademicByStartDateAndEndDate(LocalDate start, LocalDate end, UUID schoolId);
}
