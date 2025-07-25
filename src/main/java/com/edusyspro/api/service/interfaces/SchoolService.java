package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.School;

public interface SchoolService {
    School getSchool(String schoolId);
    int updateSchoolField(String schoolId, UpdateField fields);
}
