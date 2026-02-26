package com.edusyspro.api.resource.pdf;

import com.edusyspro.api.finance.dto.PaymentHistory;
import com.edusyspro.api.resource.io.PdfGenerator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.edusyspro.api.helper.UtilHelper.getPaymentMethodText;

@Component
public class PaymentReceiptPdfGenerator extends PdfGenerator<PaymentHistory> {

    public PaymentReceiptPdfGenerator(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    @Override
    protected String getTemplateName() {
        return "pdf/receipt";
    }

    @Override
    protected void populateSpecificContext(Context context, PaymentHistory data) {
        context.setVariable("invoice", data.getInvoice());
        context.setVariable("student", data.getEnrolledStudent());
        context.setVariable("guardian", data.getGuardian());
        context.setVariable("paymentMethodText", getPaymentMethodText(data.getPaymentMethod()));
    }

    @Override
    protected String getPdfDescription(PaymentHistory data) {
        return "Reçu " + data.getVoucherNumber();
    }

    @Override
    public boolean supports(Class<?> dataClass) {
        return PaymentHistory.class.isAssignableFrom(dataClass);
    }
}
