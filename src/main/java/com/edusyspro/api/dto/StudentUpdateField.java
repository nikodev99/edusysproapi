package com.edusyspro.api.dto;

import java.io.Serializable;

public record StudentUpdateField(String field, Object value) implements Serializable {
}
