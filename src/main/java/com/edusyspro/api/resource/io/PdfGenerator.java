package com.edusyspro.api.resource.io;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.model.School;
import com.edusyspro.api.resource.AbstractFileGenerator;
import com.edusyspro.api.resource.FileGenerationException;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.function.Function;

public abstract class PdfGenerator<T> extends AbstractFileGenerator<T> {
    protected final TemplateEngine templateEngine;

    public PdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    protected abstract String getTemplateName();

    protected abstract String getPdfDescription(T data);

    protected abstract void populateSpecificContext(Context context, T data);

    @Override
    public void generate(CustomUserDetails user, T data, School school, ByteArrayOutputStream outputStream) throws FileGenerationException {
        validData(data);

        try {
            Context context = new Context();

            context.setVariable("school", school);
            context.setVariable("data", data);
            context.setVariable("datetimeFormatter", (Function<ZonedDateTime, String>) this::formatDateTime);
            context.setVariable("dateFormatter", (Function<LocalDate, String>) this::formatDate);
            context.setVariable("currencyFormatter", (Function<BigDecimal, String>) this::formatCurrency);

            populateSpecificContext(context, data);

            String html = templateEngine.process(getTemplateName(), context);

            ITextRenderer renderer = new ITextRenderer();

            registerFontsFromClasspath(renderer);

            String TEMPLATES_BASE_CLASSPATH = "templates/";
            URL base = getClass().getClassLoader().getResource(TEMPLATES_BASE_CLASSPATH);
            String baseUrl = (base != null) ? base.toString() : null;

            renderer.setDocumentFromString(html, baseUrl);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            writePdfInfo(user, renderer, school, getPdfDescription(data));

            renderer.finishPDF();

        }catch (Exception e){
            throw new FileGenerationException("Failed to generate PDF File: " + e.getMessage());
        }
    }

    private void registerFontsFromClasspath(ITextRenderer renderer) throws FileGenerationException {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            String FONTS_CLASSPATH_DIR = "templates/fonts/";
            Resource[] resources = resolver.getResources("classpath*:" + FONTS_CLASSPATH_DIR + "*.ttf");
            for (Resource resource : resources) {
                System.out.println("RESOURCE: " + resource.getFilename());
                try {
                    File f = resource.getFile();
                    String path = f.getAbsolutePath();
                    renderer.getFontResolver().addFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                }catch (Exception e){
                    try {
                        String url =resource.getURL().toString();
                        renderer.getFontResolver().addFont(url, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    } catch (Exception ex2) {
                        // ignore single font failures
                    }
                }

            }
        }catch (Exception e){
            throw new FileGenerationException("Failed to registering fonts to PDF file: " + e.getMessage());
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

}
