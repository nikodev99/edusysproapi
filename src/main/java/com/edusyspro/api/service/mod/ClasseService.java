package com.edusyspro.api.service.mod;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.StudentBossDTO;
import com.edusyspro.api.dto.TeacherBossDTO;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.service.impl.ClasseServiceImp;
import com.edusyspro.api.service.interfaces.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService extends ClasseServiceImp {

    public ClasseService(
            ClasseRepository classeRepository,
            PlanningService planningService,
            ScheduleService scheduleService,
            ClasseBossService<TeacherBossDTO> classeTeacherBossService,
            ClasseBossService<StudentBossDTO> classeStudentBossService,
            SchoolService SchoolService
    ) {
        super(
            classeRepository,
            planningService,
            scheduleService,
            classeTeacherBossService,
            classeStudentBossService,
            SchoolService
        );
    }

    public Page<ClasseDTO> getAllClassesBySchool(String school, Pageable pageable) {
        return fetchAll(school, pageable);
    }

    public List<ClasseDTO> getAllClassesBySchool(String school, String classeName) {
        return fetchAll(school, classeName);
    }

    public ClasseDTO getClasseById(int classeId, String academicYear) {
        return fetchOneById(classeId, academicYear);
    }

    public ClasseDTO getClasseById(int classeId) {
        return fetchOneById(classeId);
    }

    public List<ClasseDTO> getClassBasicValues(String schoolId) {
        return fetchAll(schoolId);
    }

}
