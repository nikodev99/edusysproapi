package com.edusyspro.api.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public abstract class ListJsonConverter<T> implements AttributeConverter<List<T>, String> {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final Class<T> clazz;

    protected ListJsonConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String convertToDatabaseColumn(List<T> ts) {
        if (ts == null) return null;
        try {
            return mapper.writeValueAsString(ts);
        }catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert List<" + clazz.getSimpleName() +
                    "> to JSON string", e);
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            return mapper.readValue(dbData, listType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert JSON string to List<" +
                    clazz.getSimpleName() + ">", e);
        }
    }
}
