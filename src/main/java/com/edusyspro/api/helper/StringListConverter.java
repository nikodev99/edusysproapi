package com.edusyspro.api.helper;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StringListConverter extends ListJsonConverter<String> {
    public StringListConverter() {
        super(String.class);
    }
}
