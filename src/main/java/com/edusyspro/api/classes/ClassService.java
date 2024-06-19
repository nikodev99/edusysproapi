package com.edusyspro.api.classes;

import com.edusyspro.api.classes.dtos.ClassBasicValue;

import java.util.List;
import java.util.UUID;

public interface ClassService {

    List<Classe> getClasses();

    List<ClassBasicValue> getClassBasicValues(UUID id);

}
