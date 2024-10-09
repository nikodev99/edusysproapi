package com.edusyspro.api.dto.custom;

import java.io.Serializable;

public record UpdateField(String field, Object value) implements Serializable {
}
