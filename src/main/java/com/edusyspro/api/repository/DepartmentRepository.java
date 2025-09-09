package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.DepartmentBasicValue;
import com.edusyspro.api.dto.custom.DepartmentEssential;
import com.edusyspro.api.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.custom.DepartmentBasicValue(d.id, d.name, d.code, d.purpose) from Department d where d.school.id = ?1
    """)
    Optional<List<DepartmentBasicValue>> findBasicDepartmentBySchool(UUID schoolId);

    @Query("select new com.edusyspro.api.dto.custom.DepartmentEssential(" +
            "d.id, d.name, d.code, d.purpose, i.id, b.current, i.firstName, " +
            "i.lastName, i.image, b.startPeriod, b.endPeriod)" +
            "from Department d left join d.boss b left join  b.d_boss i where d.school.id = ?1 and d.code = ?2")
    Optional<DepartmentEssential> findDepartmentByCode(UUID schoolId, String code);

    @Query("select new com.edusyspro.api.dto.custom.DepartmentBasicValue(d.id, d.name, d.code, d.purpose) from Department d where d.code = ?1 and d.school.id = ?2")
    Optional<DepartmentBasicValue> findDepartmentByCodeAndSchoolId(String code, UUID schoolId);

    @Query("select count(d.id) from Department d where d.school.id = ?1 and d.name = ?2 and d.code = ?3")
    Long departmentExistsByNameOrCode(UUID schoolId, String name, String code);
}
