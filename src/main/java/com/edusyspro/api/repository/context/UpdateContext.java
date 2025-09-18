package com.edusyspro.api.repository.context;

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

    private int updateEntityField(Class<?> entityClass, String field, Object value, Object entityId) {
        return  PatchContext.updateEntityField(entityManager, entityClass, field, value, entityId);
    }

    private int updateEntityField(Class<?> entityClass, String field, Object value, Object entityId, String modifyField) {
        return  PatchContext.updateEntityField(entityManager, entityClass, field, value, entityId, modifyField);
    }
}
