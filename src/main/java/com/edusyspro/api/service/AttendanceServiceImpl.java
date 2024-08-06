package com.edusyspro.api.service;

import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Page<Attendance> getLastAttendance(String schoolID, Pageable pageable) {
        return attendanceRepository.findAttendanceByStudentId(UUID.fromString(schoolID), pageable);
    }

}
