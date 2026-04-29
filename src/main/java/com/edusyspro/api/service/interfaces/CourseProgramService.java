package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.CourseProgramDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.dto.custom.CourseProgramResponse;
import com.edusyspro.api.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseProgramService {
    CourseProgramResponse findAllProgramsByTeacherCourseAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear);

    List<CourseProgramDTO> findAllProgramsByTeacherAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear);

}
