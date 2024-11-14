package com.edusyspro.api.dto;

import java.io.Serializable;

public record UpdateField(String field, Object value) implements Serializable {
}
