package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.Guardian;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GuardianService {

    GuardianEntity saveOrUpdateGuardian(GuardianEntity guardian);

    Guardian findGuardianById(String id);

    Guardian findGuardianByIdWithStudents(String guardianId);

    List<Guardian> findAll();

}
