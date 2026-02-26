package com.edusyspro.api.resource.pdf;

import com.edusyspro.api.finance.dto.InvoiceDTO;
import com.edusyspro.api.resource.io.PdfGenerator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.function.Function;

import static com.edusyspro.api.helper.UtilHelper.getInvoiceStatusColor;
import static com.edusyspro.api.helper.UtilHelper.getInvoiceStatusText;


@Component
public class InvoicePdfGenerator extends PdfGenerator<InvoiceDTO> {

    public InvoicePdfGenerator(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    @Override
    protected String getTemplateName() {
        return "pdf/invoice";
    }

    @Override
    protected void populateSpecificContext(Context context, InvoiceDTO data) {
        context.setVariable("student", data.getEnrolledStudent());
        context.setVariable("guardian", data.getGuardian());
        context.setVariable("items", data.getItems());
        context.setVariable("discountFormatter", (Function<Double, String>) this::formatDiscount);
        context.setVariable("statusText", getInvoiceStatusText(data.getStatus()));
        context.setVariable("statusColor", getInvoiceStatusColor(data.getStatus()));
    }

    @Override
    protected String getPdfDescription(InvoiceDTO data) {
        return "Facture " + data.getInvoiceNumber();
    }

    @Override
    public boolean supports(Class<?> dataClass) {
        return InvoiceDTO.class.isAssignableFrom(dataClass);
    }
}
