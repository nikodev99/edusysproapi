package com.edusyspro.api.dto.custom;
import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.model.enums.Section;

public record ClassBasicValue (
        int id,
        String name,
        String category,
        Section section,
        String department,
        String code
){
    public ClasseDTO toClasse(){
        return ClasseDTO.builder()
                .id(id)
                .name(name)
                .category(category)
                .grade(GradeDTO.builder()
                        .section(section)
                        .build())
                .department(DepartmentDTO.builder()
                        .name(department)
                        .code(code)
                        .build())
                .build();
    }
}
