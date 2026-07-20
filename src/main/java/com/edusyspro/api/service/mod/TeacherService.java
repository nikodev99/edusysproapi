package com.edusyspro.api.service.mod;

import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.repository.CourseRepository;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.repository.TeacherSchoolAffiliationRepository;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.impl.TeacherServiceImpl;
import com.edusyspro.api.service.interfaces.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService extends TeacherServiceImpl {
    public TeacherService(
            TeacherRepository teacherRepository,
            TeacherSchoolAffiliationRepository teacherSchoolAffiliationRepository,
            ScheduleService scheduleService,
            AcademicYearService academicYearService,
            UpdateContext updateContext,
            CourseProgramService courseProgramService,
            IndividualReferenceService individualReferenceService,
            ReprimandService reprimandService,
            TeachingReportService teachingReportService,
            ClasseRepository classeRepository,
            CourseRepository courseRepository
    ) {
        super(
                teacherRepository,
                teacherSchoolAffiliationRepository,
                scheduleService ,
                academicYearService,
                updateContext,
                courseProgramService,
                individualReferenceService,
                reprimandService,
                teachingReportService,
                classeRepository,
                courseRepository
        );
    }

    public TeacherDTO saveTeacher(TeacherDTO teacherDTO) {
        return save(teacherDTO);
    }

    public Page<TeacherDTO> findAllTeachers(String schoolId, Pageable pageable) {
        return fetchAll(schoolId, pageable);
    }

    public Page<TeacherDTO> findAllTeachers(String schoolId, String teacherId, Pageable pageable) {
        return fetchAll(pageable, schoolId, teacherId);
    }

    public List<TeacherDTO> findAllTeachers(String schoolId, String lastName) {
        return fetchAll(schoolId, lastName);
    }

    public TeacherDTO findTeacherById(String id, String schoolId) {
        return fetchOneById(UUID.fromString(id), schoolId);
    }

    public List<TeacherDTO> findAllClasseTeachers(int classeId) {
        return fetchAllByOtherEntityId(String.valueOf(classeId));
    }

    public List<TeacherDTO> findAllTeacherBasicValue(int classeId, Section section) {
        return fetchAllByOtherEntityId(String.valueOf(classeId), section.toString());
    }

    public TeacherDTO findTeacherBasicValue(long teacherId, int classeId) {
        return fetchOneById(teacherId, classeId);
    }

    public int updateTeacherField(String id, UpdateField updateField) {
        return patch(UUID.fromString(id), updateField);
    }
}
