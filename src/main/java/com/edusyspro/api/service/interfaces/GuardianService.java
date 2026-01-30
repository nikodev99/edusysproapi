package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.model.GuardianEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface GuardianService {

    GuardianEntity saveOrUpdateGuardian(GuardianEntity guardian, String reference);

    GuardianDTO findGuardianById(String id);

    GuardianDTO findGuardianByIdWithStudents(String guardianId);

    UUID getGuardianId(Long personalInfoId);

    List<GuardianDTO> findAll();

}
