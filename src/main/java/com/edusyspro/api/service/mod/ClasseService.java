package com.edusyspro.api.service.mod;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.service.impl.ClasseServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService extends ClasseServiceImp {

    public ClasseService(ClasseRepository classeRepository) {
        super(classeRepository);
    }

    public Page<ClasseDTO> getAllClassesBySchool(String school, Pageable pageable) {
        return fetchAll(school, pageable);
    }

    public List<ClasseDTO> getAllClassesBySchool(String school, String classeName) {
        return fetchAll(school, classeName);
    }

    public List<ClasseDTO> getClassBasicValues(String schoolId) {
        return fetchAll(schoolId);
    }

}
