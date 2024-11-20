package com.edusyspro.api.repository.context;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Teacher;
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
public class UpdateContext {

    private final EntityManager entityManager;

    public UpdateContext(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Modifying
    @Transactional
    public int updateAddressByField(String field, Object value, long addressId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Address> update = cb.createCriteriaUpdate(Address.class);
        Root<Address> address = update.from(Address.class);

        update.set(address.get(field), value)
                .where(cb.equal(address.get("id"), addressId));

        return entityManager.createQuery(update).executeUpdate();
    }

    @Modifying
    @Transactional
    public int updatePersonalInfoByField(String field, Object value, long infoId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Individual> update = cb.createCriteriaUpdate(Individual.class);
        Root<Individual> personalInfo = update.from(Individual.class);

        value = adjustDate("birthDate", field, value);

        update.set(personalInfo.get(field), value)
                .where(cb.equal(personalInfo.get("id"), infoId));

        return entityManager.createQuery(update).executeUpdate();
    }

    @Modifying
    @Transactional
    public int updateTeacherField(String field, Object value, UUID teacherId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Teacher> update = cb.createCriteriaUpdate(Teacher.class);
        Root<Teacher> teacher = update.from(Teacher.class);

        value = adjustDate("hireDate", field, value);

        update.set(teacher.get(field), value).set(teacher.get("modifyAt"), Datetime.systemDatetime())
                .where(cb.equal(teacher.get("id"), teacherId));

        return entityManager.createQuery(update).executeUpdate();
    }

    private Object adjustDate(String field, String existingField, Object value) {
        if (field.equals(existingField)) {
            ZonedDateTime zonedDateTime = Datetime.zonedDateTime((String) value);
            value = zonedDateTime.toLocalDate();
        }
        return value;
    }
}
