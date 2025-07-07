package com.edusyspro.api.dto.custom;

import java.util.UUID;

public record SchoolBasic(
        UUID id,
        String name,
        String abbr,
        String websiteURL
) {
}
