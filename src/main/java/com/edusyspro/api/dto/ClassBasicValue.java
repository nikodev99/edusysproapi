package com.edusyspro.api.dto;
import com.edusyspro.api.model.enums.Section;

public record ClassBasicValue (
        int id,
        String name,
        String category,
        Section section
){}
