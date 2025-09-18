package com.edusyspro.api.auth.user;

import com.edusyspro.api.repository.context.PatchContext;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountUpdate {

    private final EntityManager entityManager;

    public UserAccountUpdate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public int updateUserAccountField(String field, Object value, long accountId) {
        return PatchContext.updateEntityField(entityManager, UserSchoolRole.class, field, value, accountId);
    }

}
