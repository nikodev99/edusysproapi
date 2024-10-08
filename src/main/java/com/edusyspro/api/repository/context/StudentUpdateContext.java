package com.edusyspro.api.repository.context;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.StudentEntity;
import com.edusyspro.api.service.interfaces.HealthConditionService;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public class StudentUpdateContext {

    private final EntityManager entityManager;

    public StudentUpdateContext(EntityManager entityManager, HealthConditionService healthConditionService) {
        this.entityManager = entityManager;
    }

    @Modifying
    @Transactional
    public int updateStudentByField(String field, Object value, UUID studentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<StudentEntity> update = cb.createCriteriaUpdate(StudentEntity.class);
        Root<StudentEntity> student = update.from(StudentEntity.class);

        if ("birthDate".equals(field) && value instanceof String) {
            ZonedDateTime zonedDateTime = Datetime.zonedDateTime((String) value);
            value = zonedDateTime.toLocalDate();
        }
        update.set(student.get(field), value).set(student.get("modifyAt"), Datetime.systemDatetime())
                .where(cb.equal(student.get("id"), studentId));

        return entityManager.createQuery(update).executeUpdate();
    }

    @Modifying
    @Transactional
    public int updateStudentHealthCondition(HealthCondition healthCondition, UUID studentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<StudentEntity> update = cb.createCriteriaUpdate(StudentEntity.class);
        Root<StudentEntity> student = update.from(StudentEntity.class);
        update.set(student.get("healthCondition"), healthCondition)
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

    @Modifying
    @Transactional
    public int updateStudentGuardianByField(String field, Object value, UUID guardianId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<GuardianEntity> update = cb.createCriteriaUpdate(GuardianEntity.class);
        Root<GuardianEntity> guardian = update.from(GuardianEntity.class);

        update.set(guardian.get(field), value)
                .set(guardian.get("modifyAt"), Datetime.systemDatetime())
                .where(cb.equal(guardian.get("id"), guardianId));

        return entityManager.createQuery(update).executeUpdate();
    }

}
