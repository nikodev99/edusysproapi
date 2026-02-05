package com.edusyspro.api.downloads;

import com.edusyspro.api.finance.entities.Invoice;
import com.edusyspro.api.finance.entities.Payments;
import com.edusyspro.api.finance.repos.InvoiceRepository;
import com.edusyspro.api.finance.repos.PaymentRepository;
import com.edusyspro.api.model.School;
import com.edusyspro.api.repository.SchoolRepository;
import com.edusyspro.api.resource.FileGenerationException;
import com.edusyspro.api.resource.FileGenerator;
import com.edusyspro.api.resource.io.InvoicePdfGenerator;
import com.edusyspro.api.resource.io.PaymentReceiptPdfGenerator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FinanceFileGeneratorService {
    private final List<FileGenerator<?>> fileGenerators;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final SchoolRepository schoolRepository;

    public FinanceFileGeneratorService(List<FileGenerator<?>> fileGenerators, InvoiceRepository invoiceRepository, PaymentRepository paymentRepository, SchoolRepository schoolRepository) {
        this.fileGenerators = fileGenerators;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
        this.schoolRepository = schoolRepository;
    }

    public byte[] generateInvoice(Long invoiceId, String schoolId) throws FileGenerationException {
        FileGenerator<Invoice> file = findGenerator(InvoicePdfGenerator.class);
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new FileGenerationException("Invoice not found"));
        School school = findSchool(schoolId);
        return generateWithGenerator(invoice, school, file);
    }

    public byte[] generatePaymentReceipt(String paymentId, String schoolId) throws FileGenerationException {
        FileGenerator<Payments> file = findGenerator(PaymentReceiptPdfGenerator.class);
        Payments payment = paymentRepository.findById(UUID.fromString(paymentId)).orElseThrow(() -> new FileGenerationException("Payment not found"));
        School school = findSchool(schoolId);
        return generateWithGenerator(payment, school, file);
    }

    private <T> byte[] generateWithGenerator(T data, School school, FileGenerator<T> generator)
            throws FileGenerationException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        generator.generate(data, school, outputStream);
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

    private School findSchool(String schoolId) throws FileGenerationException {
        return schoolRepository.findById(UUID.fromString(schoolId)).orElseThrow(() -> new FileGenerationException("School not found"));
    }
}
