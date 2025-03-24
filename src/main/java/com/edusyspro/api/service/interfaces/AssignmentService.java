package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;

import java.util.List;
import java.util.Map;

public interface AssignmentService {

    List<AssignmentDTO> findAllClasseAssignments(Integer classeId, String academicYear);

    List<AssignmentDTO> findAllClasseExamAssignments(Integer classeId, String academicYear, Long examId);

    List<AssignmentDTO> findAllClasseAssignmentsBySubject(Integer classeId, String academicYear, Integer subjectId);

    List<AssignmentDTO> findSomeAssignmentsPreparedByTeacher(long teacherId);

    List<AssignmentDTO> findAllAssignmentsPreparedByTeacher(long teacherId);

    List<AssignmentDTO> findAllAssignmentsPreparedByTeacherByCourse(long teacherId, CourseAndClasseIds ids);

    List<AssignmentDTO> findAllAssignmentsPreparedByTeacher(long teacherId, CourseAndClasseIds ids);

    Map<String, Boolean> updateAssignmentDates(AssignmentDTO assignmentDTO, long assignmentId);

    Map<String, Boolean> removeAssignment(Long id);

}
