package com.edusyspro.api.student.services;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.repository.ScheduleRepository;
import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.entities.HealthCondition;
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
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ScheduleRepository scheduleRepository) {
        this.studentRepository = studentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Student findStudentById(String id) {
        Student student = Student.builder().build();
        StudentEntity studentEntity = studentRepository.findStudentEntityById(UUID.fromString(id));
        BeanUtils.copyProperties(studentEntity, student);
        return student;
    }

    @Override
    public Address getStudentAddress(String studentId) {
        return studentRepository.findStudentEntityAddressByStudentId(UUID.fromString(studentId))
                .orElseThrow();
    }

    @Override
    public GuardianEntity getStudentGuardian(String studentId) {
        return studentRepository.getStudentEntityGuardianByStudentId(UUID.fromString(studentId))
                .orElseThrow();
    }

    @Override
    public HealthCondition getStudentHealthCondition(String studentId) {
        return studentRepository.findStudentEntityHealthConditionByStudentId(UUID.fromString(studentId))
                .orElse(null);
    }
}
