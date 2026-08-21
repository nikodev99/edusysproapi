package com.edusyspro.api.mail;

import com.edusyspro.api.auth.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailBodyFactory {
    @Value("${ui.email-base-url}")
    private String uiBaseUrl;

    @Value("${ui.support-mail}")
    private String supportEmail;

    public EmailBodies create(User userInfo, String linkPath) {
        return new EmailBodies(userInfo, linkPath, uiBaseUrl, supportEmail);
    }

    public EmailBodies create(User userInfo, String linkPath, String clearPassword) {
        return new EmailBodies(userInfo, linkPath, clearPassword, uiBaseUrl, supportEmail);
    }
}
