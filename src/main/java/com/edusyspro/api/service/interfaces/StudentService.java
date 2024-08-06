package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.dto.Student;

public interface StudentService {
    Student findStudentById(String id);

    Address getStudentAddress(String studentId);

    GuardianEntity getStudentGuardian(String studentId);

    HealthCondition getStudentHealthCondition(String studentId);
}
