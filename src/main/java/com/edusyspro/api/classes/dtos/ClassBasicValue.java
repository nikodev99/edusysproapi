package com.edusyspro.api.classes.dtos;


import com.edusyspro.api.entities.enums.Section;

public record ClassBasicValue (
        int id,
        String name,
        String category,
        Section section
){}
