package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentService {

    List<AssignmentDTO> findSomeAssignmentsPreparedByTeacher(long teacherId);

    Page<AssignmentDTO> findAllAssignmentsPreparedByTeacher(long teacherId, CourseAndClasseIds ids, Pageable pageable);
}
