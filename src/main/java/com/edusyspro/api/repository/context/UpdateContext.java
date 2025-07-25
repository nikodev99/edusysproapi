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
        return updateEntityField(Address.class, field, value, addressId);
    }

    @Modifying
    @Transactional
    public int updatePersonalInfoByField(String field, Object value, long infoId) {
        value = adjustDate("birthDate", field, value);
        return updateEntityField(Individual.class, field, value, infoId);
    }

    @Modifying
    @Transactional
    public int updateTeacherField(String field, Object value, UUID teacherId) {
        value = adjustDate("hireDate", field, value);
        return updateEntityField(Teacher.class, field, value, teacherId, "modifyAt");
    }

    @Modifying
    @Transactional
    public int updateEmployeeFields(String field, Object value, UUID employeeId) {
        value = adjustDate("hireDate", field, value);
        value = adjustDate("birthDate", field, value);
        return updateEntityField(Employee.class, field, value, employeeId, "modifyAt");
    }

    @Modifying
    @Transactional
    public int updateAssignmentField(String field, Object value, long assignmentId) {
        value = adjustDate("examDate", field, value);
        value = adjustTime("startTime", field, value);
        value = adjustTime("endTime", field, value);
        return updateEntityField(Assignment.class, field, value, assignmentId, "updatedDate");
    }

    @Modifying
    @Transactional
    public int updateSchoolField(String field, Object value, String schoolId) {
        return updateEntityField(Schedule.class, field, value, UUID.fromString(schoolId), "modifyAt");
    }

    @Modifying
    @Transactional
    public int updateAcademicYearField(String field, Object value, String academicYearId) {
        value = adjustDate("startDate", field, value);
        value = adjustDate("endDate", field, value);
        return updateEntityField(AcademicYear.class, field, value, UUID.fromString(academicYearId));
    }

    @Modifying
    @Transactional
    public int updateGradeField(String field, Object value, int gradeId) {
        return updateEntityField(Grade.class, field, value, gradeId, "modifyAt");
    }

    @Modifying
    @Transactional
    public int updateDepartmentField(String field, Object value, int departmentId) {
        return updateEntityField(Department.class, field, value, departmentId, "modifyAt");
    }

    private <T> int updateEntityField(Class<T> entityClass, String field, Object value, Object entityId) {
        return updateEntityField(entityClass, field, value, entityId, null);
    }

    private  <T> int updateEntityField(Class<T> entityClass, String field, Object value, Object entityId, String modifyAtField) {
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

    private <T, Q> Path<Q> resolvePath(Root<T> root, String field) {
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
