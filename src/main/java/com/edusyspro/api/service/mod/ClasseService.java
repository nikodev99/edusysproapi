package com.edusyspro.api.service.mod;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.repository.GradeRepository;
import com.edusyspro.api.service.impl.ClasseServiceImp;
import com.edusyspro.api.service.interfaces.ClasseStudentBossService;
import com.edusyspro.api.service.interfaces.ClasseTeacherBossService;
import com.edusyspro.api.service.interfaces.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService extends ClasseServiceImp {

    public ClasseService(
            ClasseRepository classeRepository,
            GradeRepository gradeRepository,
            ScheduleService scheduleService,
            ClasseTeacherBossService classeTeacherBossService,
            ClasseStudentBossService classeStudentBossService,
            TeacherService teacherService
    ) {
        super(
            classeRepository,
            gradeRepository,
            scheduleService,
            classeTeacherBossService,
            classeStudentBossService,
            teacherService
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

    public List<ClasseDTO> getClassBasicValues(String schoolId) {
        return fetchAll(schoolId);
    }

}
