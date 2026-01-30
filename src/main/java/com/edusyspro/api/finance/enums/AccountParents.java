package com.edusyspro.api.finance.enums;

public enum AccountParents {
    CLASS_4(4,  "410000", "Third-party / Receivables (students, suppliers)"),
    CLASS_5(5,  "500000", "Cash & Bank"),
    CLASS_6(6,  "600000", "Expenses"),
    CLASS_7(7,  "700000", "Revenue (tuition & related revenues)"),
    CLASS_8(8,  "800000", "Equity & Provisions"),
    CLASS_9(9,  "900000", "Off-balance / Statistical / Special accounts");


    private final int code;
    private final String label;
    private final String parentCode;

    AccountParents(int code, String parentCode, String label) {
        this.code = code;
        this.parentCode = parentCode;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getParentCode() {
        return parentCode;
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
