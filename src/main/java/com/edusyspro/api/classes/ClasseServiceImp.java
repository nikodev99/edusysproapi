package com.edusyspro.api.classes;

import com.edusyspro.api.classes.dtos.ClassBasicValue;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClasseServiceImp implements ClasseService {
    private final ClasseRepository classeRepository;

    @Override
    public List<Classe> getClasses() {
        List<ClasseEntity> classeEntity = classeRepository.findAll();
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
        return classeRepository.findAllBasicValue(id);
    }
}
