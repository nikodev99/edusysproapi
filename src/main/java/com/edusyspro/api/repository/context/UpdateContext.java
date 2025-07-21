package com.edusyspro.api.repository.context;

import com.edusyspro.api.model.*;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
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

    @Modifying
    @Transactional
    public int updateEmployeeFields(String field, Object value, UUID employeeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Employee> update = cb.createCriteriaUpdate(Employee.class);
        Root<Employee> employee = update.from(Employee.class);

        value = adjustDate("hireDate", field, value);
        value = adjustDate("birthDate", field, value);

        update.set(employee.get(field), value).set(employee.get("modifyAt"), Datetime.systemDatetime())
                .where(cb.equal(employee.get("id"), employeeId));

        return entityManager.createQuery(update).executeUpdate();
    }

    @Modifying
    @Transactional
    public int updateAssignmentField(String field, Object value, long assignmentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Assignment> update = cb.createCriteriaUpdate(Assignment.class);
        Root<Assignment> assignment = update.from(Assignment.class);

        value = adjustDate("examDate", field, value);
        value = adjustTime("startTime", field, value);
        value = adjustTime("endTime", field, value);
        Path<Object> path = resolvePath(assignment, field);

        update.set(path, value)
                .set(assignment.get("updatedDate"), Datetime.systemDatetime())
                .where(cb.equal(assignment.get("id"), assignmentId));

        return entityManager.createQuery(update).executeUpdate();
    }

    private Object adjustDate(String field, String existingField, Object value) {
        if (field.equals(existingField)) {
            ZonedDateTime zonedDateTime = Datetime.zonedDateTime((String) value);
            value = zonedDateTime.toLocalDate();
        }
        return value;
    }

    private Object adjustTime(String field, String existingField, Object value) {
        if (field.equals(existingField)) {
            ZonedDateTime zonedDateTime = Datetime.zonedDateTime((String) value);
            value = zonedDateTime.toLocalTime();
        }
        return value;
    }

    private <T> Path<T> resolvePath(Root<Assignment> root, String field) {
        if (field.contains(".")) {
            String[] parts = field.split("\\.");
            Path<T> p = root.get(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                p = p.get(parts[i]);
            }
            return p;
        }
        // simple (non‐nested) field
        return root.get(field);
    }
}
