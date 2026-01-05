package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ReprimandDTO;
import com.edusyspro.api.dto.filters.ReprimandFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReprimandService {

    Long createReprimand(ReprimandDTO reprimandDTO);

    List<ReprimandDTO> findAllReprimandsByStudentId(String student_id);

    Page<ReprimandDTO> findStudentReprimands(ReprimandFilters filters, Pageable pageable);

    Page<ReprimandDTO> findClasseReprimands(ReprimandFilters filters, Pageable pageable);

    List<ReprimandDTO> fetchSomeStudentReprimandedByTeacher(long teacherId);

    Page<ReprimandDTO> fetchAllStudentReprimandedByTeacher(long teacherId, String academicYear, Pageable pageable);

}
