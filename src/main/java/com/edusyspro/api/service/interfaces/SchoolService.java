package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;

import java.util.List;
import java.util.UUID;

public interface SchoolService {
    School getSchool(String schoolId);
    School getSchoolByAcademicYear(String academicYear);
    UUID getSchoolIdByAcademicYear(UUID academicYear);
    List<Section> getSections(String schoolId);
    int updateSchoolField(String schoolId, UpdateField fields);

    UUID getSchoolByGrade(Integer gradeId);
}
