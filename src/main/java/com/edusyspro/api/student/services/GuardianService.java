package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.Guardian;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GuardianService {

    Guardian findGuardianById(String id);

    List<Guardian> findAll();

}
