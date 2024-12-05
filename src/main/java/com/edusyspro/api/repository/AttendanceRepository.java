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

    @Query("select a from Attendance a where a.studentEntity.id = ?1 and a.academicYear.current = true order by a.attendanceDate desc")
    Page<Attendance> findAttendanceByStudentId(UUID studentId, Pageable pageable);

    Page<Attendance> findAttendanceByStudentEntityIdAndAcademicYearIdOrderByAttendanceDateDesc(UUID studentId, UUID academicYearId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.AttendanceEssential(a.id, a.attendanceDate, a.status) from Attendance a where a.studentEntity.id = ?1 and a.academicYear.id = ?2")
    List<AttendanceEssential> findAllByStudentEntityIdAndAcademicYearId(UUID studentId, UUID academicYearId);
}
