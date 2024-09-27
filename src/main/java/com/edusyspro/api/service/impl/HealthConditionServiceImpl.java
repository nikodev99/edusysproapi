package com.edusyspro.api.service.impl;

import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.enums.Blood;
import com.edusyspro.api.repository.HealthConditionRepository;
import com.edusyspro.api.service.interfaces.HealthConditionService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthConditionServiceImpl implements HealthConditionService {

    private final EntityManager entityManager;
    private final HealthConditionRepository healthConditionRepository;

    @Autowired
    public HealthConditionServiceImpl(EntityManager entityManager, HealthConditionRepository healthConditionRepository) {
        this.entityManager = entityManager;
        this.healthConditionRepository = healthConditionRepository;
    }

    @Override
    public HealthCondition saveStudentHealthCondition(HealthCondition incommingHealthCondition) {
        HealthCondition healthCondition = HealthCondition.builder()
                .weight(0)
                .height(0)
                .bloodType(Blood.A)
                .build();
        if (incommingHealthCondition != null) {
            return entityManager.merge(incommingHealthCondition);
        }else {
            return healthConditionRepository.save(healthCondition);
        }
    }
}
