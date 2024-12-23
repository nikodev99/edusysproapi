package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ReprimandDTO;
import com.edusyspro.api.model.Reprimand;
import com.edusyspro.api.model.enums.PunishmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReprimandService {

    List<Reprimand> findStudentReprimand(long studentId);

    List<ReprimandDTO> fetchSomeStudentReprimandedByTeacher(long teacherId);

    Page<ReprimandDTO> fetchAllStudentReprimandedByTeacher(long teacherId, int classeId, Pageable pageable);

}
