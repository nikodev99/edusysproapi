package com.edusyspro.api.repository.context;

import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public final class PatchContext {

    public static <T> int updateEntityField(EntityManager entityManager, Class<T> entityClass, String field, Object value, Object entityId) {
        return updateEntityField(entityManager, entityClass, field, value, entityId, null);
    }

    public static  <T> int updateEntityField(EntityManager entityManager, Class<T> entityClass, String field, Object value, Object entityId, String modifyAtField) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> update = cb.createCriteriaUpdate(entityClass);
        Root<T> root = update.from(entityClass);


        Path<Object> path = resolvePath(root, field);
        update.set(path, value);

        // Optionally update the modification timestamp field
        if (modifyAtField != null && !modifyAtField.trim().isEmpty()) {
            update.set(root.get(modifyAtField), Datetime.systemDatetime());
        }

        update.where(cb.equal(root.get("id"), entityId));

        return entityManager.createQuery(update).executeUpdate();
    }

    private static <T, Q> Path<Q> resolvePath(Root<T> root, String field) {
        if (field.contains(".")) {
            String[] parts = field.split("\\.");
            Path<Q> p = root.get(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                p = p.get(parts[i]);
            }
            return p;
        }
        // simple (non‐nested) field
        return root.get(field);
    }

}
