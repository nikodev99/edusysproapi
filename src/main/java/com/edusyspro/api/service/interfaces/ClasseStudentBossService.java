package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.StudentBossDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClasseStudentBossService {
    Page<StudentBossDTO> fetchAllStudentBoss(int classeId, Pageable pageable);
    List<StudentBossDTO> fetchAllStudentBoss(int classeId, String academicYearId);
    StudentBossDTO fetchStudentBoss(int classeId);
}
