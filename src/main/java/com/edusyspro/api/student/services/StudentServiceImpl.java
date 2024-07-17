package com.edusyspro.api.student.services;

import com.edusyspro.api.student.entities.StudentEntity;
import com.edusyspro.api.student.models.Student;
import com.edusyspro.api.student.repos.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findStudentById(String id) {
        Student student = Student.builder().build();
        StudentEntity studentEntity = studentRepository.findStudentEntityById(UUID.fromString(id));
        BeanUtils.copyProperties(studentEntity, student);
        return student;
    }
}
