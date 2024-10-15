package com.edusyspro.api.dto;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.enums.Section;

public record ClassBasicValue (
        int id,
        String name,
        String category,
        Section section
){
    public Classe toClasse(){
        return Classe.builder()
                .id(id)
                .name(name)
                .category(category)
                .grade(Grade.builder()
                        .section(section)
                        .build())
                .build();
    }
}
