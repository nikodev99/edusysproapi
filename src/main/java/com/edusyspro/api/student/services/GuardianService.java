package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.Guardian;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface GuardianService {

    Guardian findGuardianById(UUID id);

    List<Guardian> findAll();

}
