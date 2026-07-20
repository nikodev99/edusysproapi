package com.edusyspro.api.dto;

import com.edusyspro.api.model.TeacherClasses;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherClasseDTO {
    private Long id;
    private ClasseDTO classe;
    private TeacherDTO teacher;
    private SchoolAffiliationDTO affiliation;

    public TeacherClasses toEntity() {
        return TeacherClasses.builder()
                .id(id)
                .classe(ClasseDTO.toEntity(classe))
                .build();
    }

    public static TeacherClasses toEntity(TeacherClasseDTO dto) {
        return TeacherClasses.builder()
                .id(dto.getId())
                .classe(ClasseDTO.toEntity(dto.getClasse()))
                .build();
    }

    public TeacherClasseDTO toDto() {
        return TeacherClasseDTO.builder()
                .id(id)
                .classe(classe)
                .teacher(teacher)
                .build();
    }

    public static TeacherClasseDTO toDto(TeacherClasses classe) {
        return TeacherClasseDTO.builder()
                .id(classe.getId())
                .classe(ClasseDTO.fromEntity(classe.getClasse()))
                .build();
    }
}
