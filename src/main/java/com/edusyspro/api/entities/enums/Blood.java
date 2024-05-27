package com.edusyspro.api.entities.enums;

import lombok.Getter;

@Getter
public enum Blood {
    A("A+"),
    B("B+"),
    O("O+"),
    AB("AB+"),
    A_("A-"),
    B_("B-"),
    O_("O-"),
    AB_("AB-");

    private final String bloodType;

    Blood(String bloodType) {
        this.bloodType = bloodType;
    }
}
