package com.edusyspro.api.student.services;

import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.models.Guardian;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GuardianService {

    GuardianEntity saveOrUpdateGuardian(GuardianEntity guardian);

    Guardian findGuardianById(String id);

    List<Guardian> findAll();

}
