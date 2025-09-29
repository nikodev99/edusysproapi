package com.edusyspro.api.mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class EmailService implements EmailSender {
    private final JavaMailSender mailSender;
    private final String defaultFrom;

    public EmailService(JavaMailSender mailSender, Environment env) {
        this.mailSender = mailSender;
        this.defaultFrom = env.getProperty("app.mail.from");
    }

    @Override
    public void send(EmailRequest request) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String from = request.getFrom() != null ? request.getFrom() : defaultFrom;
        helper.setFrom(from);

        List<String> to = request.getTo();
        if (CollectionUtils.isEmpty(to)) throw new IllegalArgumentException("At least one recipient (to) is required");
        helper.setTo(to.toArray(new String[0]));

        if (request.getCc() != null && !request.getCc().isEmpty()) {
            helper.setCc(request.getCc().toArray(new String[0]));
        }

        if (request.getBcc() != null && !request.getBcc().isEmpty()) {
            helper.setBcc(request.getBcc().toArray(new String[0]));
        }

        helper.setSubject(request.getSubject() == null ? "(no subject)" : request.getSubject());
        if (request.getHtmlBody() != null && !request.getHtmlBody().isEmpty()) {
            helper.setText(request.getHtmlBody(), true);
            if (request.getTextBody() != null && !request.getTextBody().isEmpty()) {
                helper.setText(request.getTextBody(), request.getHtmlBody());
            }
        }else  {
            helper.setText(request.getTextBody() != null ? request.getTextBody() : "", false);
        }

        Map<String, byte[]> attachments = request.getAttachments();
        if (attachments != null && !attachments.isEmpty()) {
            for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
                helper.addAttachment(entry.getKey(), new ByteArrayResource(entry.getValue()));
            }
        }

        mailSender.send(mimeMessage);
    }
}
