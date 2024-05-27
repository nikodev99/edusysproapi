package com.edusyspro.api.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = true)
public class JpaConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return Text.listToArrayStringLike(attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Text.arrayStringLikeToList(dbData);
    }
}
