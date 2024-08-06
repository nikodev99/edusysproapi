package com.edusyspro.api.repository;

import com.edusyspro.api.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("select a from Attendance a where a.studentEntity.id = ?1 and a.academicYear.current = true order by a.attendanceDate desc")
    Page<Attendance> findAttendanceByStudentId(UUID studentId, Pageable pageable);

}
