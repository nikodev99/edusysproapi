package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.SchoolRepository;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.interfaces.SchoolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class SchoolServiceImp implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final UpdateContext updateContext;

    public SchoolServiceImp(SchoolRepository schoolRepository, UpdateContext updateContext) {
        this.schoolRepository = schoolRepository;
        this.updateContext = updateContext;
    }

    @Override
    public School getSchool(String schoolId) {
        return schoolRepository.getSchoolById(UUID.fromString(schoolId));
    }

    @Override
    public List<Section> getSections(String schoolId) {
        return schoolRepository.getSchoolSections(UUID.fromString(schoolId));
    }

    @Override
    public int updateSchoolField(String schoolId, UpdateField fields) {
        return updateContext.updateSchoolField(fields.field(), fields.value(), schoolId);
    }
}
