package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.dto.custom.StudentEssential;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.repository.ScheduleRepository;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.StudentEntity;
import com.edusyspro.api.repository.StudentRepository;
import com.edusyspro.api.repository.context.StudentUpdateContext;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.interfaces.HealthConditionService;
import com.edusyspro.api.service.interfaces.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudentUpdateContext studentUpdateContext;
    private final HealthConditionService healthConditionService;
    private final UpdateContext updateContext;

    @Autowired
    public StudentServiceImpl(
            StudentRepository studentRepository,
            ScheduleRepository scheduleRepository,
            StudentUpdateContext studentUpdateContext,
            HealthConditionService healthConditionService,
            UpdateContext updateContext
    ) {
        this.studentRepository = studentRepository;
        this.scheduleRepository = scheduleRepository;
        this.studentUpdateContext = studentUpdateContext;
        this.healthConditionService = healthConditionService;
        this.updateContext = updateContext;
    }

    @Override
    public StudentDTO findStudentById(String id) {
        StudentDTO studentDTO = StudentDTO.builder().build();
        StudentEntity studentEntity = studentRepository.findStudentEntityById(UUID.fromString(id));
        BeanUtils.copyProperties(studentEntity, studentDTO);
        return studentDTO;
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

    @Override
    public int updateStudent(String field, Object value, String studentId) {
        return studentUpdateContext.updateStudentByField(field, value, UUID.fromString(studentId));
    }

    @Override
    public int updateStudentPersonalInfo(String field, Object value, long infoId) {
        return updateContext.updatePersonalInfoByField(field, value, infoId);
    }

    @Override
    public int updateStudentAddress(String field, Object value, long addressId) {
        return updateContext.updateAddressByField(field, value, addressId);
    }

    @Override
    public int updateStudentHealth(String field, Object value, String studentId) {
        HealthCondition healthCondition = getStudentHealthCondition(studentId);
        if (healthCondition != null && healthCondition.getId() != null) {
            return updateContext.updateHealthCondition(field, value, healthCondition.getId());
        }else {
            return pullAndSaveHealthCondition(field, value, studentId);
        }
    }

    @Override
    public int updateStudentGuardian(String field, Object value, String guardianId) {
        return studentUpdateContext.updateStudentGuardianByField(field, value, UUID.fromString(guardianId));
    }

    @Override
    public List<StudentDTO> findStudentByGuardian(String guardianId) {
        return studentRepository.findStudentByGuardianId(UUID.fromString(guardianId)).stream()
                .map(StudentEssential::toStudent)
                .toList();
    }

    private int pullAndSaveHealthCondition(String field, Object value, String studentId) {
        HealthCondition condition = healthConditionService.saveStudentHealthCondition(null);
        int updated = studentUpdateContext.updateStudentHealthCondition(condition, UUID.fromString(studentId));
        return updated > 0 ? studentUpdateContext.updateHealthByField(field, value, UUID.fromString(studentId)) : 0;
    }
}
