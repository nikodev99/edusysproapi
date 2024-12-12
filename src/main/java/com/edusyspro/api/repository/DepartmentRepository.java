package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.DepartmentEssential;
import com.edusyspro.api.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select new com.edusyspro.api.dto.custom.DepartmentEssential(" +
            "d.id, d.name, d.code, d.purpose, d.boss.d_boss.id, d.boss.current, d.boss.d_boss.personalInfo.firstName, " +
            "d.boss.d_boss.personalInfo.lastName, d.boss.d_boss.personalInfo.image, d.boss.startPeriod, d.boss.endPeriod)" +
            "from Department d where d.code = ?1")
    Optional<DepartmentEssential> findDepartmentByCode(String code);

}
