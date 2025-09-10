package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;

import java.util.List;

public interface SchoolService {
    School getSchool(String schoolId);
    List<Section> getSections(String schoolId);
    int updateSchoolField(String schoolId, UpdateField fields);
}
