package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.TeacherBossDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClasseTeacherBossService {

    Page<TeacherBossDTO> fetchAllTeacherBoss(int classeId, Pageable pageable);
    List<TeacherBossDTO> fetchAllTeacherBoss(int classeId, String academicYearId);
    TeacherBossDTO fetchTeacherBoss(int classeId);

}
