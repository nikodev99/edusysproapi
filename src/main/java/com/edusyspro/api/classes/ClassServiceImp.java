package com.edusyspro.api.classes;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassServiceImp implements ClassService {
    private final ClassRepository classRepository;

    @Override
    public List<Classe> getClasses() {
        List<ClasseEntity> classeEntity = classRepository.findAll();
        return classeEntity.stream()
                .map(entity -> {
                    Classe classe = new Classe();
                    BeanUtils.copyProperties(entity, classe);
                    return classe;
                })
                .toList();
    }

    @Override
    public List<ClassBasicValue> getClassBasicValues(UUID id) {
        List<Tuple> classes = classRepository.findAllBasicValue(id);
        return classes.stream()
                .map(tuple -> ClassBasicValue.builder()
                        .id(Integer.parseInt(tuple.get(0).toString()))
                        .name(tuple.get(1).toString())
                        .category(tuple.get(2).toString())
                        .build())
                .toList();
    }
}
