package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.AttendanceEssential;
import com.edusyspro.api.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status, a.classeEntity.name, " +
            "a.classeEntity.category, a.classeEntity.grade.section) from Attendance a where a.individual.id = ?1 " +
            "and a.academicYear.current = true order by a.attendanceDate desc")
    Page<AttendanceEssential> findAttendanceByStudentId(long studentId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status, a.classeEntity.name, " +
            "a.classeEntity.category, a.classeEntity.grade.section) from Attendance a where a.individual.id = ?1 " +
            "and a.academicYear.id = ?2 order by a.attendanceDate desc")
    Page<AttendanceEssential> findAttendanceByStudentAndAcademicYear(long studentId, UUID academicYearId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status, a.classeEntity.name, " +
            "a.classeEntity.category, a.classeEntity.grade.section) from Attendance a where a.individual.id = ?1 " +
            "and a.academicYear.id = ?2")
    List<AttendanceEssential> findAllByStudentAndAcademicYear(long studentId, UUID academicYearId);

    @Query("select new com.edusyspro.api.dto.custom.IndividualBasic(a.individual.firstName, a.individual.lastName, a.individual.image), a.status, count(a.status) " +
            "from Attendance a where a.individual.id = ?1 and a.academicYear.id = ?2")
    List<Object[]> findStudentAttendanceStatus(long studentId, UUID academicYearId);

    @Query("SELECT new com.edusyspro.api.dto.custom.IndividualBasic(a.individual.firstName, a.individual.lastName, a.individual.image), " +
            "a.status, COUNT(a.status) FROM Attendance a WHERE a.classeEntity.id = ?1 AND a.academicYear.id = ?2 " +
            "GROUP BY a.individual.id, a.individual.firstName, a.individual.lastName, a.individual.image, a.status")
    List<Object[]> findClasseAttendanceStatus(int classeId, UUID academicYearId);

    @Query("select a.status, count(a.status) from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2 group by a.status")
    Page<Object[]> findAttendanceStatusCountByClasse(int classeId, UUID academicYearId, Pageable pageable);

    @Query("select a.status, count(a.status) from Attendance a where a.individual.id = ?1 and a.academicYear.id = ?2 group by a.status")
    List<Object[]> findAttendanceStatusCountByStudent(long studentId, UUID academicYearId);

    @Query("select a.status, count(a.status) from Attendance a where a.academicYear.school.id = ?1 and a.academicYear.id = ?2 group by a.status")
    List<Object[]> findAttendanceStatusCountBySchool(UUID schoolId, UUID academicYearId);

    @Query("select distinct a.attendanceDate from Attendance a where a.classeEntity.id = ?1 and a.status is not null order by a.attendanceDate desc")
    List<LocalDate> findRecentAttendanceDate(int classeId, UUID academicYearId, Pageable pageable);

    @Query("""
        select a.status, a.attendanceDate, count(a.status) from Attendance a where a.classeEntity.id = ?1 and
        a.attendanceDate in (?2) and a.academicYear.id = ?3 group by a.attendanceDate, a.status order by a.attendanceDate
    """)
    List<Object[]> findRecentClasseAttendanceStatsPerStatus(int classeId, List<LocalDate> dates, UUID academicYearId);
}
