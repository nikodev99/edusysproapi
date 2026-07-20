package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.dto.filters.AssignmentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AssignmentService {

    AssignmentDTO addNewAssignment(AssignmentDTO assignmentData);

    Page<AssignmentDTO> findAllAssignments(AssignmentFilter filter, Pageable pageable);

    List<AssignmentDTO> findAllNotCompleteAssignment(String academicYear, Long teacherId);

    List<AssignmentDTO> findAllClasseAssignments(Integer classeId, String academicYear);

    List<AssignmentDTO> findAllClasseExamAssignments(Integer classeId, String academicYear, Long examId);

    List<AssignmentDTO> findAllClasseAssignmentsBySubject(Integer classeId, String academicYear, Integer subjectId);

    List<AssignmentDTO> findAllCourseAssignments(Integer courseId, String academicYear);

    List<AssignmentDTO> findSomeAssignmentsPreparedByTeacher(long teacherId, String schoolId);

    List<AssignmentDTO> findAllAssignmentsPreparedByTeacher(long teacherId, String academicYear);

    List<AssignmentDTO> findAllAssignmentsPreparedByTeacherByCourse(long teacherId, String academicYear, CourseAndClasseIds ids);

    List<AssignmentDTO> findAllAssignmentsPreparedByTeacher(long teacherId, String academicYear, CourseAndClasseIds ids);

    AssignmentDTO findAssignmentById(long id);

    int patchUpdateAssignment(UpdateField assignment, long assignmentId);

    Map<String, Boolean> updateAssignmentDates(AssignmentDTO assignmentDTO, long assignmentId);

    Map<String, Boolean> removeAssignment(Long id);

}
