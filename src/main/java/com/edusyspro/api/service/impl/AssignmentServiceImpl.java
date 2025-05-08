package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.custom.AssignmentEssential;
import com.edusyspro.api.dto.custom.AssignmentToExam;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.dto.filters.AssignmentFilter;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.model.Assignment;
import com.edusyspro.api.repository.AssignmentRepository;
import com.edusyspro.api.repository.spec.AssignmentSpec;
import com.edusyspro.api.service.interfaces.AssignmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.beans.beancontext.BeanContextServiceAvailableEvent;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSpec assignmentSpec;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentSpec assignmentSpec) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentSpec = assignmentSpec;
    }

    @Override
    public AssignmentDTO addNewAssignment(AssignmentDTO assignmentData) {
        if (!assignmentExists(assignmentData)) {
            Assignment assignment = assignmentData.toEntity();
            assignmentRepository.save(assignment);
            return assignmentData;
        }
        return null;
    }

    @Override
    public Page<AssignmentDTO> findAllAssignments(AssignmentFilter filter, Pageable pageable) {
        return assignmentSpec.findAllSearchAndFilteredAssignments(filter, pageable)
                .map(AssignmentEssential::toDTO);
    }

    @Override
    public List<AssignmentDTO> findAllNotCompleteAssignment(String academicYear) {
        return assignmentRepository.findAllNotCompleteAssignments(UUID.fromString(academicYear)).stream()
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findAllClasseAssignments(Integer classeId, String academicYear) {
        return assignmentRepository.findAllClasseAssignments(classeId, UUID.fromString(academicYear)).stream()
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findAllClasseExamAssignments(Integer classeId, String academicYear, Long examId) {
        return assignmentRepository.findAllClasseAssignmentsByExam(classeId, UUID.fromString(academicYear), examId).stream()
                .map(AssignmentToExam::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findAllClasseAssignmentsBySubject(Integer classeId, String academicYear, Integer subjectId) {
        return assignmentRepository.findAllClasseAssignmentsBySubject(classeId, UUID.fromString(academicYear), subjectId).stream()
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findAllCourseAssignments(Integer courseId, String academicYear) {
        return assignmentRepository.findAllSubjectAssignments(courseId, UUID.fromString(academicYear)).stream()
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findSomeAssignmentsPreparedByTeacher(long teacherId) {
        return assignmentRepository.findAssignmentsByTeacher(teacherId, PageRequest.of(0, 5))
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findAllAssignmentsPreparedByTeacher(long teacherId) {
        return assignmentRepository.findAssignmentsByTeacher(teacherId).stream()
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findAllAssignmentsPreparedByTeacherByCourse(long teacherId, CourseAndClasseIds ids) {
        return assignmentRepository.findAllAssignmentsByTeacher(teacherId, ids.classId(), ids.courseId()).stream()
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public List<AssignmentDTO> findAllAssignmentsPreparedByTeacher(long teacherId, CourseAndClasseIds ids) {
        return assignmentRepository.findAllAssignmentsByTeacher(teacherId, ids.classId()).stream()
                .map(AssignmentEssential::toDTO)
                .toList();
    }

    @Override
    public Map<String, Boolean> updateAssignmentDates(AssignmentDTO assignmentDTO, long assignmentId) {
        int hasUpdated = assignmentRepository.changeAssignmentDateById(
                assignmentDTO.getExamDate(),
                assignmentDTO.getStartTime(),
                assignmentDTO.getEndTime(),
                assignmentId
        );
        if (hasUpdated > 0) {
            return Map.of("updated", Boolean.TRUE);
        }
        return Map.of("updated", Boolean.FALSE);
    }

    @Override
    public Map<String, Boolean> removeAssignment(Long id) {
        boolean isRemoved = false;
        try {
            assignmentRepository.deleteById(id);
            isRemoved = true;
        }catch (Exception ignored){}
        return Map.of("status", isRemoved);
    }

    private boolean assignmentExists(AssignmentDTO assignment) {
        Long count = assignmentRepository.assignmentExists(assignment.getExamName())
                .orElseThrow(() -> new AlreadyExistException("Le devoir avec le nom '" + assignment.getExamName() + "' existe déjà cette année."));
        return count > 0;
    }
}
