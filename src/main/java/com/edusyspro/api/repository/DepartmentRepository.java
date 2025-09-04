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
        select new com.edusyspro.api.dto.custom.DepartmentBasicValue(d.id, d.name, d.code) from Department d where d.school.id = ?1
    """)
    Optional<List<DepartmentBasicValue>> findBasicDepartmentBySchool(UUID schoolId);

    @Query("select new com.edusyspro.api.dto.custom.DepartmentEssential(" +
            "d.id, d.name, d.code, d.purpose, d.boss.d_boss.id, d.boss.current, d.boss.d_boss.firstName, " +
            "d.boss.d_boss.lastName, d.boss.d_boss.image, d.boss.startPeriod, d.boss.endPeriod)" +
            "from Department d where d.code = ?1")
    Optional<DepartmentEssential> findDepartmentByCode(UUID schoolId, String code);

    @Query("select new com.edusyspro.api.dto.custom.DepartmentBasicValue(d.id, d.name, d.code) from Department d where d.code = ?1 and d.school.id = ?2")
    Optional<DepartmentBasicValue> findDepartmentByCodeAndSchoolId(String code, UUID schoolId);

    @Query("select count(d.id) from Department d where d.school.id = ?1 and d.name = ?2 and d.code = ?3")
    Long departmentExistsByNameOrCode(UUID schoolId, String name, String code);
}
