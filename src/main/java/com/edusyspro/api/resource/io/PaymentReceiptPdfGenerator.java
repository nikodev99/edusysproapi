package com.edusyspro.api.resource.io;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.finance.dto.PaymentHistory;
import com.edusyspro.api.finance.entities.Payments;
import com.edusyspro.api.model.School;
import com.edusyspro.api.resource.AbstractFileGenerator;
import com.edusyspro.api.resource.FileGenerationException;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.function.Function;

import static com.edusyspro.api.helper.UtilHelper.getPaymentMethodText;

@Component
public class PaymentReceiptPdfGenerator extends AbstractFileGenerator<PaymentHistory> {
    private final TemplateEngine templateEngine;

    public PaymentReceiptPdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void generate(CustomUserDetails user, PaymentHistory data, School school, ByteArrayOutputStream outputStream) throws FileGenerationException {
        validData(data);

        try {
            Context context = new Context();
            context.setVariable("school", school);
            context.setVariable("payment", data);
            context.setVariable("invoice", data.getInvoice());
            context.setVariable("student", data.getEnrolledStudent());
            context.setVariable("guardian", data.getGuardian());
            context.setVariable("datetimeFormatter", (Function<ZonedDateTime, String>) this::formatDateTime);
            context.setVariable("dateFormatter", (Function<LocalDate, String>) this::formatDate);
            context.setVariable("currencyFormatter", (Function<BigDecimal, String>) this::formatCurrency);
            context.setVariable("paymentMethodText", getPaymentMethodText(data.getPaymentMethod()));

            String htmlContent = templateEngine.process("pdf/receipt", context);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            writePdfInfo(user, renderer, school, "Reçu " + data.getVoucherNumber());

        } catch (Exception e) {
            throw new FileGenerationException("Failed to generate receipt PDF", e);
        }
    }

    @Override
    public String getMimeType() {
        return MimeType.PDF.getMimeType();
    }

    @Override
    public String getFileExtension() {
        return MimeType.PDF.getPrimaryExtension().orElse("templates");
    }

    @Override
    public boolean supports(Class<?> dataClass) {
        return PaymentHistory.class.isAssignableFrom(dataClass);
    }
}
