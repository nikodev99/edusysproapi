package com.edusyspro.api.resource;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.model.School;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;

public abstract class AbstractFileGenerator<T> implements FileGenerator<T> {
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    protected static final DecimalFormat NUMBER_FORMAT = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.FRANCE);

    static {
        DecimalFormatSymbols symbols = NUMBER_FORMAT.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('\u00A0');
        NUMBER_FORMAT.setDecimalFormatSymbols(symbols);
        NUMBER_FORMAT.setGroupingUsed(true);
        NUMBER_FORMAT.setMaximumFractionDigits(2);
        NUMBER_FORMAT.setMinimumFractionDigits(0);
        NUMBER_FORMAT.setCurrency(Currency.getInstance("XAF"));
    }

    protected void writePdfInfo(CustomUserDetails user, ITextRenderer render, School school, String title) {
        PdfWriter writer = render.getWriter();

        writer.getInfo().put(PdfName.TITLE, new PdfString(title, PdfObject.TEXT_UNICODE));

        if (user.getUsername() != null) {
            writer.getInfo().put(PdfName.AUTHOR, new PdfString(user.getUsername(), PdfObject.TEXT_UNICODE));
        }
        if (school.getId() != null) {
            writer.getInfo().put(PdfName.AUTHOR, new PdfString(school.getName(), PdfObject.TEXT_UNICODE));
        }
        if (user.getEmail() != null) {
            writer.getInfo().put(PdfName.KEYWORDS, new PdfString(user.getEmail(), PdfObject.TEXT_UNICODE));
        }
    }

    protected String formatCurrency(BigDecimal amount) {
        if (amount == null) return "0";
        return NUMBER_FORMAT.format(amount) + ' ' + NUMBER_FORMAT.getCurrency().getSymbol(Locale.FRANCE);
    }

    protected String formatDate(LocalDate dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_FORMATTER);
    }

    protected String formatTime(ZonedDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(TIME_FORMATTER);
    }

    protected String formatDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    protected String formatDiscount(Double percentage) {
        return percentage != null && percentage > 0 ? Math.round(percentage * 100) + "%" : "0";
    }

    protected String safeString(String str) {
        if (str == null) return "";
        return str;
    }

    protected void validData(T data) throws FileGenerationException {
        if (data == null) {
            throw new FileGenerationException("Cannot generate file: data is null");
        }
    }
}
