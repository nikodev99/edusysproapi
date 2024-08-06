package com.edusyspro.api.service;

import com.edusyspro.api.dto.ClassBasicValue;
import com.edusyspro.api.dto.Classe;

import java.util.List;
import java.util.UUID;

public interface ClasseService {

    List<Classe> getClasses();

    List<ClassBasicValue> getClassBasicValues(UUID id);

}
