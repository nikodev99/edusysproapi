package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.dto.Student;

import java.util.List;

public interface StudentService {
    Student findStudentById(String id);

    Address getStudentAddress(String studentId);

    GuardianEntity getStudentGuardian(String studentId);

    HealthCondition getStudentHealthCondition(String studentId);

    int updateStudent(String field, Object value, String studentId);
    int updateStudentAddress(String field, Object value, long addressId);
    int updateStudentHealth(String field, Object value, String studentId);

    List<Student> findStudentByGuardian(String guardianId);
}
