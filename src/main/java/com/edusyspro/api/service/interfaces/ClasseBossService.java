package com.edusyspro.api.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClasseBossService<DTO> {
    int saveClasseBoss(DTO classeBossDTO);
    Page<DTO> fetchAllClasseBosses(int classeId, Pageable pageable);
    List<DTO> fetchAllClasseBosses(int classeId, String academicYearId);
    DTO fetchCurrentClasseBoss(int classeId);
    boolean checkPrincipal(Object ...args);
}
