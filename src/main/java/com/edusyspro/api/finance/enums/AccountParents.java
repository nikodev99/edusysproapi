package com.edusyspro.api.finance.enums;

import lombok.Getter;

@Getter
public enum AccountParents {
    CLASS_4(4,  "410", "Third-party / Receivables (students, suppliers)"),
    CLASS_5(5,  "500", "Cash & Bank"),
    CLASS_6(6,  "600", "Expenses"),
    CLASS_7(7,  "700", "Revenue (tuition & related revenues)"),
    CLASS_8(8,  "800", "Equity & Provisions"),
    CLASS_9(9,  "900", "Off-balance / Statistical / Special accounts");


    private final int code;
    private final String label;
    private final String parentCode;

    AccountParents(int code, String parentCode, String label) {
        this.code = code;
        this.parentCode = parentCode;
        this.label = label;
    }

    public static AccountParents fromCode(int code) {
        for (AccountParents account : AccountParents.values()) {
            if (account.getCode() == code) {
                return account;
            }
        }
        throw new IllegalArgumentException("unknown AccountParents code " + code);
    }
}
