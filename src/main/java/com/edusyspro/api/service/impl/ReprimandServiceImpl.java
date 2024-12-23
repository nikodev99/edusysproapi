package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ReprimandDTO;
import com.edusyspro.api.dto.custom.ReprimandEssential;
import com.edusyspro.api.model.Reprimand;
import com.edusyspro.api.model.enums.PunishmentStatus;
import com.edusyspro.api.repository.ReprimandRepository;
import com.edusyspro.api.service.interfaces.ReprimandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReprimandServiceImpl implements ReprimandService {

    private final ReprimandRepository reprimandRepository;

    @Autowired
    public ReprimandServiceImpl(ReprimandRepository reprimandRepository) {
        this.reprimandRepository = reprimandRepository;
    }

    @Override
    public List<Reprimand> findStudentReprimand(long studentId) {
        return reprimandRepository.findReprimandsByStudentId(studentId)
                .orElse(List.of());
    }

    @Override
    public List<ReprimandDTO> fetchSomeStudentReprimandedByTeacher(long teacherId) {
        Pageable pageable = PageRequest.of(0, 5);
        return reprimandRepository.findStudentReprimandByTeacher(teacherId, PunishmentStatus.COMPLETED, "DIFF", pageable)
                .map(ReprimandEssential::toDTO)
                .toList();
    }

    @Override
    public Page<ReprimandDTO> fetchAllStudentReprimandedByTeacher(long teacherId, int classeId, Pageable pageable) {
        return reprimandRepository.findStudentReprimandByTeacher(teacherId, classeId, pageable)
                .map(ReprimandEssential::toDTO);
    }

}
