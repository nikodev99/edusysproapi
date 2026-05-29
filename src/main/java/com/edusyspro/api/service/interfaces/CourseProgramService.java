package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.dto.CourseProgramDTO;
import com.edusyspro.api.dto.CourseProgramRequest;
import com.edusyspro.api.dto.CourseProgramTopicDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.dto.custom.CourseProgramResponse;
import com.edusyspro.api.model.enums.ProgramStatus;
import com.edusyspro.api.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseProgramService {
    Long saveCourseProgram(CourseProgramRequest courseProgramDTO);
    Long saveCourseProgramTopic(CourseProgramTopicDTO topic);

    CourseProgramResponse findAllProgramsByTeacherCourseAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear);
    List<CourseProgramDTO> findAllProgramsByTeacherAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear);

    MessageResponse changeStatus(Long id, ProgramStatus status);
    MessageResponse completed(Long id);
    MessageResponse deleteProgram(Long id);
    MessageResponse deleteTopic(Long id);

    
}
