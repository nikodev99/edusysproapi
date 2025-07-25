package com.edusyspro.api.dto;

import com.edusyspro.api.model.Department;
import com.edusyspro.api.model.DepartmentBoss;
import com.edusyspro.api.model.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    private Integer id;
    private String name;
    private String code;
    private String purpose;
    private DepartmentBoss boss;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static DepartmentDTO fromEntity(Department department) {
        if (department == null) return null;
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .purpose(department.getPurpose())
                .school(department.getSchool())
                .createdAt(department.getCreatedAt())
                .modifyAt(department.getModifyAt())
                .build();
    }

    public static Department toEntity(DepartmentDTO dto) {
        if(dto == null) return null;
        return Department.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .purpose(dto.getPurpose())
                .school(dto.getSchool())
                .createdAt(dto.getCreatedAt())
                .modifyAt(dto.getModifyAt())
                .build();
    }
}
