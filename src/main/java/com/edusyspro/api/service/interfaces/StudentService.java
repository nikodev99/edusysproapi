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
    int updateStudentPersonalInfo(String field, Object value, long infoId);
    int updateStudentAddress(String field, Object value, long addressId);
    int updateStudentHealth(String field, Object value, String studentId);
    int updateStudentGuardian(String field, Object value, String guardianId);

    List<Student> findStudentByGuardian(String guardianId);
}
