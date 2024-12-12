package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AssignmentDTO;

import java.util.List;

public interface AssignmentService {

    List<AssignmentDTO> findSomeAssignmentsPreparedByTeacher(long teacherId);

}
