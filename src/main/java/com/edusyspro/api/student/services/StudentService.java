package com.edusyspro.api.student.services;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.entities.HealthCondition;
import com.edusyspro.api.student.models.Student;

public interface StudentService {
    Student findStudentById(String id);

    Address getStudentAddress(String studentId);

    GuardianEntity getStudentGuardian(String studentId);

    HealthCondition getStudentHealthCondition(String studentId);
}
