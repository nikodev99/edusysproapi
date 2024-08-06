package com.edusyspro.api.service;

import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.Score;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.Guardian;
import com.edusyspro.api.dto.EnrolledStudent;
import com.edusyspro.api.dto.Enrollment;
import com.edusyspro.api.dto.EnrolledStudentGuardian;
import com.edusyspro.api.repository.EnrollmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final ScoreService scoreService;
    private final AttendanceService attendanceService;
    private final StudentService studentService;

    private final GuardianService guardianService;

    @Autowired
    public EnrollmentServiceImp(EnrollmentRepository enrollmentRepository, ScoreService scoreService, AttendanceService attendanceService, StudentService studentService, GuardianService guardianService) {
        this.enrollmentRepository = enrollmentRepository;
        this.scoreService = scoreService;
        this.attendanceService = attendanceService;
        this.studentService = studentService;
        this.guardianService = guardianService;
    }

    @Override
    @Transactional
    public Enrollment enrollStudent(Enrollment enrollment) {
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder().build();
        BeanUtils.copyProperties(enrollment, enrollmentEntity);

        GuardianEntity guardianEntity = enrollmentEntity.getStudent().getGuardian();
        if (guardianEntity != null) {
            GuardianEntity guardian = guardianService.saveOrUpdateGuardian(guardianEntity);
            enrollmentEntity.getStudent().setGuardian(guardian);
        }

        enrollmentRepository.save(enrollmentEntity);
        return enrollment;
    }

    @Override
    public Page<List<EnrolledStudent>> getEnrolledStudents(String schoolId, Pageable pageable) {
        return  enrollmentRepository.findEnrolledStudent(UUID.fromString(schoolId), pageable);
    }

    @Override
    public List<EnrolledStudent> getEnrolledStudents(String schoolId, String lastname) {
        return enrollmentRepository.findEnrolledStudent(UUID.fromString(schoolId), "%" + lastname + "%");
    }

    @Override
    public Enrollment getEnrolledStudent(String schoolId, String studentId) {
        EnrolledStudent enrolledStudent = enrollmentRepository.findEnrollmentById(UUID.fromString(schoolId), UUID.fromString(studentId));
        EnrolledStudent studentEnrolled = EnrolledStudent.builder().build();
        Enrollment student = studentEnrolled.populateStudent(enrolledStudent);
        if (student != null) {
            Pageable pageable = PageRequest.of(0, 5);
            Page<Score> scores = scoreService.getLastScoresByStudent(studentId, pageable);
            Page<EnrollmentEntity> enrollments = enrollmentRepository.findStudentEnrollments(UUID.fromString(schoolId), UUID.fromString(studentId), pageable);
            Page<Attendance> attendances = attendanceService.getLastAttendance(studentId, pageable);
            student.getStudent().setAddress(studentService.getStudentAddress(studentId));
            student.getStudent().setGuardian(studentService.getStudentGuardian(studentId));
            student.getStudent().setHealthCondition(studentService.getStudentHealthCondition(studentId));
            student.getStudent().setMarks(scores.getContent());
            student.getStudent().setEnrollmentEntities(enrollments.getContent());
            student.getStudent().setAttendances(attendances.getContent());
        }
        return student;
    }

    @Override
    public List<EnrolledStudent> getStudentClassmates(String schoolId, String studentId, int classmateNumber) {
        Pageable pageable = PageRequest.of(0, classmateNumber);
        return enrollmentRepository.findStudentRandomClassmateByClasseId(
                UUID.fromString(schoolId),
                UUID.fromString(studentId),
                pageable
        ).getContent();
    }

    @Override
    public List<Guardian> getEnrolledStudentGuardians(String schoolId, boolean isArchived) {
        List<EnrolledStudentGuardian> enrolledStudentGuardian = enrollmentRepository.findEnrolledStudentGuardian(UUID.fromString(schoolId), isArchived);
        List<Guardian> guardians = new ArrayList<>();
        if (!enrolledStudentGuardian.isEmpty()) {
            guardians = enrolledStudentGuardian.stream()
                    .map(e -> Guardian.builder()
                            .id(e.getId())
                            .firstName(e.getFirstName())
                            .lastName(e.getLastName())
                            .maidenName(e.getMaidenName())
                            .genre(e.getGenre())
                            .emailId(e.getEmailId())
                            .jobTitle(e.getJobTitle())
                            .company(e.getCompany())
                            .telephone(e.getTelephone())
                            .mobile(e.getMobile())
                            .build())
                    .toList();
        }
        return guardians;
    }
}
