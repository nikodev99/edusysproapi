package com.edusyspro.api.school.services;

import com.edusyspro.api.school.entities.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceService {

    Page<Attendance> getLastAttendance(String schoolID, Pageable pageable);

}
