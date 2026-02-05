package com.edusyspro.api.helper;

import com.edusyspro.api.finance.enums.InvoiceStatus;
import com.edusyspro.api.finance.enums.PaymentMethod;

public class UtilHelper {
    public static String getInvoiceStatusText(InvoiceStatus status) {
        return switch (status) {
            case PAID -> "PAYÉE";
            case PARTIALLY_PAID -> "PARTIELLEMENT PAYÉE";
            case DRAFT -> "EN ATTENTE";
            case CANCELLED -> "ANNULÉE";
            case SENT -> "BROUILLON";
            case OVERDUE -> "EN RETARD";
        };
    }

    public static String getInvoiceStatusColor(InvoiceStatus status) {
        return switch (status) {
            case PAID -> "#52c41a";
            case PARTIALLY_PAID -> "#1890ff";
            case DRAFT -> "#faad14";
            case CANCELLED -> "#ff4d4f";
            case SENT -> "#d9d9d9";
            case OVERDUE -> "#f35e5e";
        };
    }

    public static String getPaymentMethodText(PaymentMethod method) {
        return switch (method) {
            case MOBILE_MONEY -> "Mobile Money";
            case BANK_CARD -> "Carte Bancaire";
            case BANK_TRANSFER -> "Virement Bancaire";
            case CASH -> "Espèces";
        };
    }
}
