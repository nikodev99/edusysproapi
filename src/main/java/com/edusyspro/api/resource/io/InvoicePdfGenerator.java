package com.edusyspro.api.resource.io;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.finance.dto.InvoiceDTO;
import com.edusyspro.api.finance.entities.Invoice;
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

import static com.edusyspro.api.helper.UtilHelper.getInvoiceStatusColor;
import static com.edusyspro.api.helper.UtilHelper.getInvoiceStatusText;


@Component
public class InvoicePdfGenerator extends AbstractFileGenerator<InvoiceDTO> {

    private final TemplateEngine templateEngine;

    public InvoicePdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void generate(CustomUserDetails user, InvoiceDTO data, School school, ByteArrayOutputStream outputStream) throws FileGenerationException {
        validData(data);

        try {
            Context context = new Context();
            context.setVariable("school", school);
            context.setVariable("invoice", data);
            context.setVariable("student", data.getEnrolledStudent());
            context.setVariable("guardian", data.getGuardian());
            context.setVariable("items", data.getItems());
            context.setVariable("datetimeFormatter", (Function<ZonedDateTime, String>) this::formatDateTime);
            context.setVariable("dateFormatter", (Function<LocalDate, String>) this::formatDate);
            context.setVariable("currencyFormatter", (Function<BigDecimal, String>) this::formatCurrency);
            context.setVariable("discountFormatter", (Function<Double, String>) this::formatDiscount);
            context.setVariable("statusText", getInvoiceStatusText(data.getStatus()));
            context.setVariable("statusColor", getInvoiceStatusColor(data.getStatus()));

            String html = templateEngine.process("pdf/invoice", context);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            writePdfInfo(user, renderer, school, "Facture " + data.getInvoiceNumber());
            renderer.finishPDF();

        }catch (Exception e){
            throw new FileGenerationException("Failed to generate Invoice PDF: " + e.getMessage());
        }
    }

    @Override
    public String getMimeType() {
        return MimeType.PDF.getMimeType();
    }

    @Override
    public String getFileExtension() {
        return MimeType.PDF.getExtensions().get(0);
    }

    @Override
    public boolean supports(Class<?> dataClass) {
        return InvoiceDTO.class.isAssignableFrom(dataClass);
    }


}
