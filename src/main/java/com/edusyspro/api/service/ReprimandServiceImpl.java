package com.edusyspro.api.service;

import com.edusyspro.api.entities.Reprimand;
import com.edusyspro.api.repository.ReprimandRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Reprimand> findStudentReprimand(String studentId) {
        return reprimandRepository.findReprimandsByStudentId(UUID.fromString(studentId))
                .orElse(List.of());
    }
}
