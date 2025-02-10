package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.AttendanceEssential;
import com.edusyspro.api.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

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

    @Query("select a.status, count(a.status) from Attendance a where a.classeEntity.id = ?1 and a.academicYear.id = ?2 group by a.status")
    List<Object[]> findAttendanceStatusCountByClasse(int classeId, UUID academicYearId);

    @Query("select a.status, count(a.status) from Attendance a where a.individual.id = ?1 and a.academicYear.id = ?2 group by a.status")
    List<Object[]> findAttendanceStatusCountByStudent(long studentId, UUID academicYearId);

    @Query("select a.status, count(a.status) from Attendance a where a.academicYear.school.id = ?1 and a.academicYear.id = ?2 group by a.status")
    List<Object[]> findAttendanceStatusCountBySchool(UUID schoolId, UUID academicYearId);
}
