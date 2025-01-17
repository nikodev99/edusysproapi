package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.DepartmentDTO;

public record DepartmentBasicValue(
        int id,
        String name,
        String code
) {
    public DepartmentDTO toDTO() {
        return DepartmentDTO.builder()
                .id(id)
                .name(name)
                .code(code)
                .build();
    }
}
