package com.edusyspro.api.service.mod;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.service.impl.ClasseServiceImp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService extends ClasseServiceImp {

    public ClasseService(ClasseRepository classeRepository) {
        super(classeRepository);
    }

    public List<ClasseDTO> getClasses() {
        return fetchAll();
    }

    public List<ClasseDTO> getClassBasicValues(String schoolId) {
        return fetchAll(schoolId);
    }

}
