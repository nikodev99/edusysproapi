package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.custom.GuardianEssential;
import com.edusyspro.api.model.*;
import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.dto.custom.EnrolledStudent;
import com.edusyspro.api.repository.EnrollmentRepository;
import com.edusyspro.api.repository.context.EnrollmentRepositoryContext;
import com.edusyspro.api.service.interfaces.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentRepositoryContext enrollmentRepositoryContext;

    private final ScoreService scoreService;
    private final AttendanceService attendanceService;
    private final StudentService studentService;
    private final ScheduleService scheduleService;

    private final GuardianService guardianService;

    @Autowired
    public EnrollmentServiceImp(
            EnrollmentRepository enrollmentRepository,
            EnrollmentRepositoryContext enrollmentRepositoryContext,
            ScoreService scoreService,
            AttendanceService attendanceService,
            StudentService studentService,
            ScheduleService scheduleService,
            GuardianService guardianService)
    {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentRepositoryContext = enrollmentRepositoryContext;
        this.scoreService = scoreService;
        this.attendanceService = attendanceService;
        this.studentService = studentService;
        this.scheduleService = scheduleService;
        this.guardianService = guardianService;
    }

    @Override
    @Transactional
    public EnrollmentDTO enrollStudent(EnrollmentDTO enrollmentDTO) {
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder().build();
        BeanUtils.copyProperties(enrollmentDTO, enrollmentEntity);

        GuardianEntity guardianEntity = enrollmentEntity.getStudent().getGuardian();
        if (guardianEntity != null) {
            GuardianEntity guardian = guardianService.saveOrUpdateGuardian(guardianEntity);
            enrollmentEntity.getStudent().setGuardian(guardian);
        }

        enrollmentRepository.save(enrollmentEntity);
        return enrollmentDTO;
    }

    @Override
    public Page<EnrollmentDTO> getEnrolledStudents(String schoolId, Pageable pageable) {
        return  enrollmentRepository.findEnrolledStudent(UUID.fromString(schoolId), pageable)
                .map(EnrolledStudent::populateStudent);
    }

    @Override
    public List<EnrollmentDTO> getEnrolledStudents(String schoolId, String lastname) {
        return enrollmentRepository.findEnrolledStudent(UUID.fromString(schoolId), "%" + lastname + "%").stream()
                .map(EnrolledStudent::populateStudent)
                .toList();
    }

    @Override
    public EnrollmentDTO getEnrolledStudent(String schoolId, String studentId) {
        EnrollmentDTO student = enrollmentRepository.findEnrollmentById(UUID.fromString(schoolId), UUID.fromString(studentId))
                .populateStudent();

        if (student != null) {
            Pageable pageable = PageRequest.of(0, 5);
            Page<Score> scores = scoreService.getLastScoresByStudent(studentId, pageable);
            Page<EnrollmentEntity> enrollments = enrollmentRepository.findStudentEnrollments(UUID.fromString(schoolId), UUID.fromString(studentId), pageable);
            Page<Attendance> attendances = attendanceService.getLastStudentAttendances(studentId, pageable);

            ClasseDTO classe = student.getClasse();
            List<ScheduleDTO> schedules = scheduleService.getAllClasseSchedule(classe.getId(), classe.getGrade().getSection());

            student.getStudent().setGuardian(studentService.getStudentGuardian(studentId));
            student.getStudent().setHealthCondition(studentService.getStudentHealthCondition(studentId));
            student.getStudent().setMarks(scores.getContent());
            student.getStudent().setEnrollmentEntities(enrollments.getContent());
            student.getStudent().setAttendances(attendances.getContent());
            student.getClasse().setSchedule(schedules);
        }
        return student;
    }

    @Override
    public List<EnrollmentDTO> getStudentClassmates(String schoolId, String studentId, int classeId, int classmateNumber) {
        Pageable pageable = PageRequest.of(0, classmateNumber);
        List<EnrolledStudent> enrolledStudents = enrollmentRepositoryContext.findStudentRandomClassmateByClasseId(
                UUID.fromString(schoolId),
                UUID.fromString(studentId),
                classeId,
                pageable
        ).getContent();
        return enrolledStudents.stream()
                .map(EnrolledStudent::populateStudent)
                .toList();
    }

    @Override
    public Page<EnrollmentDTO> getAllStudentClassmates(String schoolId, String studentId, int classeId, String academicYear, Pageable pageable) {
        Page<EnrolledStudent> enrolledStudents = enrollmentRepository.findStudentClassmateByAcademicYear(
                UUID.fromString(schoolId),
                UUID.fromString(studentId),
                classeId,
                UUID.fromString(academicYear),
                pageable
        );
        return enrolledStudents.map(EnrolledStudent::populateStudent);
    }

    @Override
    public Page<GuardianDTO> getEnrolledStudentGuardians(String schoolId, Pageable pageable) {
        Page<GuardianEssential> enrolledStudentGuardian = enrollmentRepository.findEnrolledStudentGuardian(UUID.fromString(schoolId), pageable);
        return enrolledStudentGuardian.map(GuardianEssential::populateGuardian);
    }

    @Override
    public List<GuardianDTO> getEnrolledStudentGuardians(String schoolId, String lastname) {
        List<GuardianEssential> guardianEssentials = enrollmentRepository.findEnrolledStudentGuardian(UUID.fromString(schoolId), "%" + lastname + "%");
        return guardianEssentials.stream()
                .map(GuardianEssential::populateGuardian)
                .toList();
    }

    @Override
    public Map<String, Long> countStudents(String schoolId) {
        return Map.of("count", enrollmentRepository.countAllStudents(UUID.fromString(schoolId)));
    }
}
