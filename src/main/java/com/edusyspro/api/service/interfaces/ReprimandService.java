package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ReprimandDTO;
import com.edusyspro.api.dto.filters.ReprimandFilters;
import com.edusyspro.api.model.Reprimand;
import com.edusyspro.api.model.enums.PunishmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReprimandService {

    Page<ReprimandDTO> findStudentReprimand(ReprimandFilters filters, Pageable pageable);

    List<ReprimandDTO> fetchSomeStudentReprimandedByTeacher(long teacherId);

    Page<ReprimandDTO> fetchAllStudentReprimandedByTeacher(long teacherId, String academicYear, Pageable pageable);

}
