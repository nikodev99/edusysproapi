package com.edusyspro.api.downloads;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.finance.dto.InvoiceDTO;
import com.edusyspro.api.finance.dto.PaymentHistory;
import com.edusyspro.api.finance.services.InvoiceService;
import com.edusyspro.api.finance.services.PaymentService;
import com.edusyspro.api.model.School;
import com.edusyspro.api.resource.FileGenerationException;
import com.edusyspro.api.resource.FileGenerator;
import com.edusyspro.api.resource.io.InvoicePdfGenerator;
import com.edusyspro.api.resource.io.PaymentReceiptPdfGenerator;
import com.edusyspro.api.service.interfaces.SchoolService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class FinanceFileGeneratorService {
    private final List<FileGenerator<?>> fileGenerators;
    private final InvoiceService invoiceRepository;
    private final PaymentService paymentService;
    private final SchoolService schoolService;

    public FinanceFileGeneratorService(
            List<FileGenerator<?>> fileGenerators,
            InvoiceService invoiceRepository,
            PaymentService paymentService,
            SchoolService schoolService
    ) {
        this.fileGenerators = fileGenerators;
        this.invoiceRepository = invoiceRepository;
        this.paymentService = paymentService;
        this.schoolService = schoolService;
    }

    public byte[] generateInvoice(CustomUserDetails user, Long invoiceId, String schoolId) throws FileGenerationException {
        FileGenerator<InvoiceDTO> file = findGenerator(InvoicePdfGenerator.class);
        InvoiceDTO invoice = invoiceRepository.getInvoiceById(invoiceId);
        School school = findSchool(schoolId);
        return generateWithGenerator(user, invoice, school, file);
    }

    public byte[] generatePaymentReceipt(CustomUserDetails user, String paymentId, String schoolId) throws FileGenerationException {
        FileGenerator<PaymentHistory> file = findGenerator(PaymentReceiptPdfGenerator.class);
        PaymentHistory payment = paymentService.getPayment(paymentId);
        School school = findSchool(schoolId);
        return generateWithGenerator(user, payment, school, file);
    }

    private <T> byte[] generateWithGenerator(CustomUserDetails user, T data, School school, FileGenerator<T> generator)
            throws FileGenerationException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        generator.generate(user, data, school, outputStream);
        return outputStream.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private <T> FileGenerator<T> findGenerator(Class<? extends FileGenerator<T>> generatorClass)
            throws FileGenerationException {
        return (FileGenerator<T>) fileGenerators.stream()
                .filter(generatorClass::isInstance)
                .findFirst()
                .orElseThrow(() -> new FileGenerationException(
                        "Generator not found: " + generatorClass.getSimpleName()));
    }

    private School findSchool(String schoolId) {
        return schoolService.getSchool(schoolId);
    }
}
