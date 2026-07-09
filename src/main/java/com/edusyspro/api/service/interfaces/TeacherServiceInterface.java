package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.dto.custom.GenderCount;
import com.edusyspro.api.model.enums.OperationType;
import com.edusyspro.api.service.CustomService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TeacherServiceInterface extends CustomService<TeacherDTO, UUID> {
    List<Map<String, Object>> countStudentsByClasse(UUID teacherId, UUID schoolId);

    /**
     * This counts the gender of teachers by gender and school
     * @param schoolId The school id.
     * @return List<GenderCount>
     */
    GenderCount countAllTeachers(String schoolId);

    Map<String, Object> fetchTeacherWidgets(String teacherId, String academicYear);

    int updateTeacherClasses(String teacherId, String schoolId, OperationType operation, List<Integer> classeIds) throws AccessDeniedException;
    int updateTeacherCourses(String teacherId, String schoolId, OperationType operation, List<Integer> coursesIds) throws AccessDeniedException;

}
