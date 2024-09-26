package com.edusyspro.api.repository.context;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.StudentEntity;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class StudentUpdateContext {

    private final EntityManager entityManager;

    public StudentUpdateContext(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Modifying
    @Transactional
    public int updateStudentByField(String field, Object value, UUID studentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<StudentEntity> update = cb.createCriteriaUpdate(StudentEntity.class);
        Root<StudentEntity> student = update.from(StudentEntity.class);

        if ("birthDate".equals(field) && value instanceof String) {
            value = LocalDate.parse((String) value);
        }
        if ("birthDate".equals(field) && value instanceof List<?> dateArray) {
            if (dateArray.size() == 3 && dateArray.get(0) instanceof Integer) {
                int year = (Integer) dateArray.get(0);
                int month = (Integer) dateArray.get(1);
                int day = (Integer) dateArray.get(2);

                value = LocalDate.of(year, month, day);
            }
        }

        update.set(student.get(field), value).set(student.get("modifyAt"), Datetime.systemDatetime())
                .where(cb.equal(student.get("id"), studentId));

        return entityManager.createQuery(update).executeUpdate();
    }

    @Modifying
    @Transactional
    public int updateAddressByField(String field, Object value, long addressId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Address> update = cb.createCriteriaUpdate(Address.class);
        Root<Address> address = update.from(Address.class);

        update.set(address.get(field), value)
                .set(address.get("modifyAt"), Datetime.systemDatetime())
                .where(cb.equal(address.get("id"), addressId));

        return entityManager.createQuery(update).executeUpdate();
    }

    @Modifying
    @Transactional
    public int updateHealthByField(String field, Object value, UUID studentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<StudentEntity> update = cb.createCriteriaUpdate(StudentEntity.class);
        Root<StudentEntity> student = update.from(StudentEntity.class);

        update.set(student.get("healthCondition").get(field), value)
                .where(cb.equal(student.get("id"), studentId));

        return entityManager.createQuery(update).executeUpdate();
    }

}
