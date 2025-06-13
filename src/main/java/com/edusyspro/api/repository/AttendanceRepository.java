package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.AttendanceEssential;
import com.edusyspro.api.dto.custom.AttendanceInfo;
import com.edusyspro.api.dto.custom.AttendanceStudentIndividual;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("""
        select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status, a.classeEntity.name,
        a.classeEntity.category, a.classeEntity.grade.section) from Attendance a where a.individual.id = ?1 and a.academicYear.current = true
        order by a.attendanceDate desc
    """)
    Page<AttendanceEssential> findAttendanceByStudentId(long studentId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status, a.classeEntity.name, " +
            "a.classeEntity.category, a.classeEntity.grade.section) from Attendance a where a.individual.id = ?1 " +
            "and a.academicYear.id = ?2 order by a.attendanceDate desc")
    Page<AttendanceEssential> findAttendanceByStudentAndAcademicYear(long studentId, UUID academicYearId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status, a.classeEntity.name, " +
            "a.classeEntity.category, a.classeEntity.grade.section) from Attendance a where a.individual.id = ?1 " +
            "and a.academicYear.id = ?2")
    List<AttendanceEssential> findAllByStudentAndAcademicYear(long studentId, UUID academicYearId);

    @Query("select new com.edusyspro.api.dto.custom.IndividualBasic(a.individual.id, a.individual.firstName, a.individual.lastName, a.individual.image), a.status, count(a.status) " +
            "from Attendance a where a.individual.id = ?1 and a.academicYear.id = ?2")
    List<Object[]> findStudentAttendanceStatus(long studentId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AttendanceStudentIndividual(a.id, a.attendanceDate, a.status, a.individual.id,
        a.individual.firstName, a.individual.lastName, a.individual.image, a.classeEntity.name,a.classeEntity.category,
        a.classeEntity.grade.section) from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2 and a.attendanceDate = ?3
    """)
    List<AttendanceStudentIndividual> findAllClasseAttendancePerDate(int classeId, UUID academicYearId, LocalDate date);

    @Query("SELECT DISTINCT a.individual.id FROM Attendance a WHERE a.classeEntity.id = ?1 AND a.academicYear.id = ?2")
    Page<Long> findClasseAttendanceStatus(int classeId, UUID academicYearId, Pageable pageable);

    @Query("SELECT DISTINCT a.individual.id FROM Attendance a WHERE a.classeEntity.id = ?1 AND a.academicYear.id = ?2 " +
            "AND (LOWER(a.individual.lastName) LIKE LOWER(?3) OR LOWER(a.individual.firstName) LIKE LOWER(?3) )")
    List<Long> findClasseAttendanceStatus(int classeId, UUID academicYearId, String name);

    @Query("SELECT new com.edusyspro.api.dto.custom.IndividualBasic(a.individual.id, a.individual.firstName, a.individual.lastName, a.individual.image), " +
            "a.status, COUNT(a.status), a.classeEntity.id, a.classeEntity.name FROM Attendance a WHERE a.individual.id in (?1) AND a.academicYear.id = ?2 " +
            "GROUP BY a.individual.id, a.individual.firstName, a.individual.lastName, a.individual.image, a.status")
    List<Object[]> findClasseAttendanceStatus(List<Long> ids, UUID academicYearId);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.IndividualBasic(a.individual.id, a.individual.firstName, a.individual.lastName, a.individual.image),
        a.status, COUNT(a.status), a.classeEntity.id, a.classeEntity.name FROM Attendance a WHERE a.classeEntity.id = :classeId
        AND a.academicYear.id = :academicYearId AND a.status = :status GROUP BY a.individual.id, a.individual.firstName,
        a.individual.lastName, a.individual.image, a.status, a.classeEntity.id, a.classeEntity.name order by count(a.status) desc
    """)
    Page<Object[]> findAttendanceStatusRanking(
            @Param("classeId") int classeId,
            @Param("academicYearId") UUID academicYearId,
            @Param("status") AttendanceStatus status,
            Pageable pageable
    );

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.IndividualBasic(a.individual.id, a.individual.firstName, a.individual.lastName, a.individual.image),
        a.status, COUNT(a.status), a.classeEntity.id, a.classeEntity.name FROM Attendance a WHERE a.academicYear.school.id = :schoolId
        AND a.academicYear.id = :academicYearId AND a.status = :status GROUP BY a.individual.id, a.individual.firstName,
        a.individual.lastName, a.individual.image, a.status, a.classeEntity.id, a.classeEntity.name order by count(a.status) desc
    """)
    Page<Object[]> findAttendanceStatusRanking(
            @Param("schoolId") UUID schoolId,
            @Param("academicYearId") UUID academicYearId,
            @Param("status") AttendanceStatus status,
            Pageable pageable
    );

    @Query("""
        select a.status as status, a.attendanceDate as attendanceDate, a.classeEntity.grade.section as section, a.individual.gender as gender
        from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2
    """)
    List<AttendanceInfo> findAttendanceInfoByClasse(Integer classeId, UUID academicYearId);

    @Query("""
        select a.status as status, a.attendanceDate as attendanceDate, a.classeEntity.grade.section as section, a.individual.gender as gender
        from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2 and a.attendanceDate = ?3
    """)
    List<AttendanceInfo> findAttendanceInfoByClasseAndDate(Integer classeId, UUID academicYearId, LocalDate date);

    @Query("select a.status, count(a.status) from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2 group by a.status")
    List<Object[]> findAttendanceStatusCountByClasse(int classeId, UUID academicYearId);

    @Query("""
        select a.status, count(a.status) from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2 and a.attendanceDate = ?3 group by a.status
    """)
    List<Object[]> findAttendanceStatusCountByClassePerDate(int classeId, UUID academicYearId, LocalDate date);

    @Query("select a.status from Attendance a where a.individual.id = ?1 and a.academicYear.id = ?2")
    List<Object[]> findAttendanceStatusCountByStudent(long studentId, UUID academicYearId);

    @Query("""
        select a.status as status, a.attendanceDate as attendanceDate, a.classeEntity.grade.section as section, a.individual.gender as gender
        from Attendance a where a.academicYear.id = ?1
    """)
    List<AttendanceInfo> findAttendanceInfoByAcademicYear(UUID academicYearId);

    @Query("""
        select a.status as status, a.attendanceDate as attendanceDate, a.classeEntity.grade.section as section, a.individual.gender as gender
        from Attendance a where a.academicYear.id = ?1 and a.attendanceDate = ?2
    """)
    List<AttendanceInfo> findAttendanceInfoByAcademicYearAndDate(UUID academicYearId, LocalDate date);

    @Query("select a.status, count(a.status) from Attendance a where a.academicYear.school.id = ?1 and a.academicYear.id = ?2 group by a.status")
    List<Object[]> findAttendanceStatusCountBySchool(UUID schoolId, UUID academicYearId);

    @Query("""
        select a.status, count(a.status) from Attendance a where a.academicYear.id = ?1 and a.attendanceDate = ?2 group by a.status
    """)
    List<Object[]> findAttendanceStatusCountBySchoolPerDate(UUID academicYearId, LocalDate date);

    @Query("""
        select a.classeEntity.grade.section, a.status, count(a.status) from Attendance a where a.academicYear.id = ?1 group by a.classeEntity.grade.section, a.status
    """)
    List<Object[]> findAttendanceStatusCountByGrade(UUID academicYearId, List<Integer> gradeIds);

    @Query("""
        select a.classeEntity.grade.section, a.status, count(a.status) from Attendance a where a.academicYear.id = ?1 and a.attendanceDate = ?2 group by a.classeEntity.grade.section, a.status
    """)
    List<Object[]> findAttendanceStatusCountByGradePerDate(UUID academicYearId, LocalDate date);

    @Query("""
        select a.individual.gender, a.status, count(a.status) from Attendance a where a.academicYear.id = ?1 group by a.individual.gender, a.status
    """)
    List<Object[]> findAttendanceStatusCountByGender(UUID academicYearId, List<Integer> gradeIds);

    @Query("""
        select a.individual.gender, a.status, count(a.status) from Attendance a where a.academicYear.id = ?1 and a.attendanceDate = ?2 group by a.individual.gender, a.status
    """)
    List<Object[]> findAttendanceStatusCountByGenderPerDate(UUID academicYearId, LocalDate date);

    @Query("""
        select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status, a.classeEntity.name,
        a.classeEntity.category, a.classeEntity.grade.section) from Attendance a where a.academicYear.school.id = ?1 and a.academicYear.id = ?2
    """)
    List<AttendanceEssential> findAllSchoolAttendance(UUID schoolId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AttendanceStudentIndividual(a.id, a.attendanceDate, a.status, a.individual.id,
        a.individual.firstName, a.individual.lastName, a.individual.image, a.classeEntity.name,a.classeEntity.category,
        a.classeEntity.grade.section) from Attendance a where a.academicYear.school.id = ?1 and a.academicYear.id = ?2 and a.attendanceDate = ?3
    """)
    Page<AttendanceStudentIndividual> findAllSchoolAttendancePerDate(UUID schoolId, UUID academicYearId, LocalDate date, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.AttendanceStudentIndividual(a.id, a.attendanceDate, a.status, a.individual.id,
        a.individual.firstName, a.individual.lastName, a.individual.image, a.classeEntity.name,a.classeEntity.category,
        a.classeEntity.grade.section) from Attendance a where a.academicYear.school.id = ?1 and a.academicYear.id = ?2 and a.attendanceDate = ?3
        and (lower(a.individual.firstName) like lower(?4) or lower(a.individual.lastName) like lower(?4))
    """)
    Page<AttendanceStudentIndividual> findAllSchoolAttendancePerDate(UUID schoolId, UUID academicYearId, LocalDate date, String search, Pageable pageable);

    @Query("""
        select distinct a.attendanceDate from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2
        and a.status is not null order by a.attendanceDate desc
    """)
    List<LocalDate> findRecentAttendanceDate(int classeId, UUID academicYearId, Pageable pageable);

    @Query("""
        select distinct a.attendanceDate from Attendance a where a.academicYear.school.id = ?1 and a.academicYear.id = ?2
        and a.status is not null order by a.attendanceDate desc
    """)
    List<LocalDate> findRecentAttendanceDate(UUID schoolId, UUID academicYearId, Pageable pageable);

    @Query("""
        select a.status, a.attendanceDate, count(a.status) from Attendance a where a.classeEntity.id = ?1 and
        a.attendanceDate in (?2) and a.academicYear.id = ?3 group by a.attendanceDate, a.status order by a.attendanceDate
    """)
    List<Object[]> findRecentClasseAttendanceStatsPerStatus(int classeId, List<LocalDate> dates, UUID academicYearId);

    @Query("""
        select a.status, a.attendanceDate, count(a.status) from Attendance a where a.classeEntity.id = ?1 and a.attendanceDate between ?2 and ?3
        and a.academicYear.id = ?4 group by a.attendanceDate, a.status order by a.attendanceDate
    """)
    List<Object[]> findClasseAttendanceStatsPerDates(int classeId, LocalDate startDate, LocalDate endDate, UUID academicYearId);

    @Query("""
        select a.status, a.attendanceDate, count(a.status) from Attendance a where a.academicYear.school.id = ?1 and
        a.attendanceDate in (?2) and a.academicYear.id = ?3 group by a.attendanceDate, a.status order by a.attendanceDate
    """)
    List<Object[]> findRecentSchoolAttendanceStatsPerStatus(UUID schoolId, List<LocalDate> dates, UUID academicYearId);

    @Query("""
        select a.status, a.attendanceDate, count(a.status) from Attendance a where a.academicYear.school.id = ?1 and a.attendanceDate between ?2 and ?3
        and a.academicYear.id = ?4 group by a.attendanceDate, a.status order by a.attendanceDate
    """)
    List<Object[]> findSchoolAttendanceStatsPerDates(UUID schoolId, LocalDate startDate, LocalDate endDate, UUID academicYearId);
}
