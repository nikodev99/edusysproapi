package com.edusyspro.api.helper;

import com.edusyspro.api.model.enums.Role;

public class RoleListConverter extends ListJsonConverter<Role> {
    public RoleListConverter() {
        super(Role.class);
    }
}
