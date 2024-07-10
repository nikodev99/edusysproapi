package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.Student;

import java.util.UUID;

public interface StudentService {
    Student findStudentById(String id);
}
