package com.edusyspro.api.mail;

import com.edusyspro.api.auth.request.ForgotPasswordRequest;
import com.edusyspro.api.auth.response.UserInfoResponse;
import com.edusyspro.api.auth.user.User;
import com.edusyspro.api.auth.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordResetTest {
    @Autowired
    private UserService userService;

    @Test
    public void testPasswordForgot() {
        ForgotPasswordRequest request = ForgotPasswordRequest.builder()
                .email("g.malonga@gmail.com")
                .build();

        userService.initiatePasswordReset(request.getEmail(), null);
    }

    @Test
    public void validateTheToken() {
        UserInfoResponse user = userService.validatePasswordResetToken("71a5681af1ef721c942dab0a14006eb9cd5f16dc2309581c67fa5dd2ac92c7be");
        System.out.println("------------------------------");
        System.out.println("User: " + user);
        System.out.println("------------------------------");

        Assertions.assertNotNull(user);
    }

    @Test
    public void resetPasswordTest() {
        boolean success = userService.resetPassword(
                "71a5681af1ef721c942dab0a14006eb9cd5f16dc2309581c67fa5dd2ac92c7be",
                "@Malonga123"
        );

        System.out.println("------------------------------");
        System.out.println("Reset Status: " + success);
        System.out.println("------------------------------");

        Assertions.assertTrue(success);
    }
}
