package com.edusyspro.api.repository.context;

import com.edusyspro.api.dto.DateRange;
import com.edusyspro.api.model.*;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.EntityManager;
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
        return updateEntityField(Individual.class, field, value, infoId);
    }

    @Modifying
    @Transactional
    public int updateHealthCondition(String field, Object value, long id) {
        return updateEntityField(HealthCondition.class, field, value, id);
    }

    @Modifying
    @Transactional
    public int updateTeacherField(String field, Object value, UUID teacherId) {
        return updateEntityField(Teacher.class, field, value, teacherId, "modifyAt");
    }

    @Modifying
    @Transactional
    public int updateEmployeeFields(String field, Object value, UUID employeeId) {
        return updateEntityField(Employee.class, field, value, employeeId, "modifyAt");
    }

    @Modifying
    @Transactional
    public int updateAssignmentField(String field, Object value, long assignmentId) {
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

    @Modifying
    @Transactional
    public int updateReprimandFields(String field, Object value, Long reprimandId) {
        return updateEntityField(Reprimand.class, field, value, reprimandId);
    }

    @Modifying
    @Transactional
    public int updatePunishmentFields(String field, Object value, Long punishmentId) {
        return updateRange(Punishment.class, field, value, punishmentId, null);
    }

    @Modifying
    @Transactional
    public int updateEmployeeContract(String field, Object value, Long contractId) {
       return updateEntityField(EmployeeContracts.class, field, value, contractId);
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

    private int updateRange(Class<?> entityClass, String field, Object value, Object entityId, String modifyField) {
        if ("dateRange".equals(field)) {
            if (!(value instanceof DateRange date)) {
                throw new IllegalArgumentException("Value must be an instance of DateRange");
            }

            // Adjust each date individually
            Object startDate = adjustDate("startDate", field, date.startDate());
            Object endDate = adjustDate("endDate", field, date.startDate());

            int startUpdate = updateEntityField(entityClass, "startDate", startDate, entityId);
            int endUpdate = updateEntityField(entityClass, "endDate", endDate, entityId);

            // Return 1 if both succeeded, otherwise 0 (or throw an exception if partial failure is bad)
            return (startUpdate > 0 && endUpdate > 0) ? 1 : 0;
        }
        return modifyField != null
                ? updateEntityField(entityClass, field, value, entityId, modifyField)
                : updateEntityField(entityClass, field, value, entityId);
    }

    private int updateEntityField(Class<?> entityClass, String field, Object value, Object entityId) {
        return  PatchContext.updateEntityField(entityManager, entityClass, field, compileeAdjustDateAndTime(field, value), entityId);
    }

    private int updateEntityField(Class<?> entityClass, String field, Object value, Object entityId, String modifyField) {
        return  PatchContext.updateEntityField(entityManager, entityClass, field, compileeAdjustDateAndTime(field, value), entityId, modifyField);
    }

    private Object compileeAdjustDateAndTime(String field, Object value) {
        return switch (field) {
            case "birthDate" -> adjustDate("birthDate", field, value);
            case "hireDate" -> adjustDate("hireDate", field, value);
            case "examDate" -> adjustDate("examDate", field, value);
            case "startTime" -> adjustTime("startTime", field, value);
            case "endTime" -> adjustTime("endTime", field, value);
            case "startDate" -> adjustDate("startDate", field, value);
            case "endDate" -> adjustDate("endDate", field, value);
            default -> value;
        };
    }
}
