package com.edusyspro.api.helper;

import jakarta.persistence.Converter;

import java.time.ZonedDateTime;

@Converter(autoApply = false)
public class ZonedDatetimeConverter extends ListJsonConverter<ZonedDateTime> {
    public ZonedDatetimeConverter() {
        super(ZonedDateTime.class);
    }
}
