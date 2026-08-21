package com.edusyspro.api.mail;

import com.edusyspro.api.auth.user.User;

public class EmailBodies {
    private static final Integer COPYRIGHT_DATE = java.time.Year.now().getValue();

    private final User userInfo;
    private final String linkPath;
    private final String uiBaseUrl;
    private final String supportEmail;
    private String clearPassword;

    EmailBodies(User userInfo, String linkPath, String uiBaseUrl, String supportEmail) {
        this.userInfo = userInfo;
        this.linkPath = linkPath;
        this.uiBaseUrl = uiBaseUrl;
        this.supportEmail = supportEmail;
    }

    EmailBodies(User userInfo, String linkPath, String clearPassword, String uiBaseUrl, String supportEmail) {
        this(userInfo, linkPath, uiBaseUrl, supportEmail);
        this.clearPassword = clearPassword;
    }

    public EmailBody getRegistration() {
        String htmlContent = """
        <!DOCTYPE html>
        <html>
        <body style="margin:0; padding:0; background-color:#f4f6f8;">
          <table width="100%%" cellpadding="0" cellspacing="0" style="padding: 40px 0;">
            <tr>
              <td align="center">
                <table width="480" cellpadding="0" cellspacing="0" style="background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.06);">
                  <tr>
                    <td style="background:#1B4F72; padding:28px; text-align:center;">
                      <h1 style="color:#ffffff; margin:0; font-size:22px;">EduSysPro</h1>
                    </td>
                  </tr>
                  <tr>
                    <td style="padding:32px;">
                      <h2 style="color:#1B4F72; margin-top:0;">Bienvenue!</h2>
                      <p style="color:#333333; line-height:1.6; font-size:15px;">
                        Nous sommes ravis de vous compter parmi nous. Votre compte a été créé avec succès.
                        Voici vos identifiants de connexion :
                      </p>
                      <table cellpadding="0" cellspacing="0" style="width:100%%; background:#f4f6f8; border-radius:6px; margin:20px 0;">
                        <tr>
                          <td style="padding:16px;">
                            <p style="margin:0 0 8px 0; color:#555555; font-size:14px;"><strong>Email :</strong> %s</p>
                            <p style="margin:0 0 8px 0; color:#555555; font-size:14px;"><strong>Identifiant :</strong> %s</p>
                            <p style="margin:0 0 8px 0; color:#555555; font-size:14px;"><strong>Téléphone :</strong> %s</p>
                            <p style="margin:0; color:#555555; font-size:14px;"><strong>Mot de passe :</strong> %s</p>
                          </td>
                        </tr>
                      </table>
                      <p style="color:#333333; line-height:1.6; font-size:15px;">
                        Nous vous recommandons de modifier votre mot de passe dès votre première connexion.
                      </p>
                      <div style="text-align:center; margin:28px 0;">
                        <a href="%s/login" style="background:#1B4F72; color:#ffffff; text-decoration:none; padding:12px 28px; border-radius:6px; font-size:14px; display:inline-block;">
                          Accéder à mon espace
                        </a>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td style="background:#f4f6f8; padding:16px; text-align:center;">
                      <p style="color:#999999; font-size:12px; margin:0;">© %d EduSysPro — Tous droits réservés</p>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </body>
        </html>
        """.formatted(userInfo.getEmail(), userInfo.getUsername(), userInfo.getPhoneNumber(), clearPassword, uiBaseUrl, COPYRIGHT_DATE);

        String textContent = """
        Bienvenue sur EduSysPro !

        Nous sommes ravis de vous compter parmi nous. Voici vos identifiants :

        Email : %s
        Identifiant : %s
        Téléphone : %s
        Mot de passe : %s

        Nous vous recommandons de modifier votre mot de passe dès votre première connexion.

        """.formatted(userInfo.getEmail(), userInfo.getUsername(), userInfo.getPhoneNumber(), clearPassword);

        return new EmailBody(htmlContent, textContent);
    }

    public EmailBody getPasswordReset() {
        String htmlContent = """
        <!DOCTYPE html>
        <html>
        <body style="margin:0; padding:0; background-color:#f4f6f8;">
          <table width="100%%" cellpadding="0" cellspacing="0" style="padding: 40px 0;">
            <tr>
              <td align="center">
                <table width="480" cellpadding="0" cellspacing="0" style="background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.06);">
                  <tr>
                    <td style="background:#1B4F72; padding:28px; text-align:center;">
                      <h1 style="color:#ffffff; margin:0; font-size:22px;">EduSysPro</h1>
                    </td>
                  </tr>
                  <tr>
                    <td style="padding:32px;">
                      <h2 style="color:#1B4F72; margin-top:0;">Réinitialisation du mot de passe</h2>
                      <p style="color:#333333; line-height:1.6; font-size:15px;">
                        Vous avez demandé la réinitialisation de votre mot de passe. Cliquez sur le bouton ci-dessous pour en choisir un nouveau.
                      </p>
                      <div style="text-align:center; margin:28px 0;">
                        <a href="%s/%s" style="background:#1B4F72; color:#ffffff; text-decoration:none; padding:12px 28px; border-radius:6px; font-size:14px; display:inline-block;">
                          Réinitialiser mon mot de passe
                        </a>
                      </div>
                      <p style="color:#333333; line-height:1.6; font-size:14px;">
                        Ce lien expirera dans <strong>une heure</strong>.
                      </p>
                      <p style="color:#999999; font-size:12px; margin-top:32px;">
                        Si vous n'êtes pas à l'origine de cette demande, ignorez simplement cet e-mail — votre mot de passe restera inchangé.
                      </p>
                    </td>
                  </tr>
                  <tr>
                    <td style="background:#f4f6f8; padding:16px; text-align:center;">
                      <p style="color:#999999; font-size:12px; margin:0;">© %d EduSysPro — Tous droits réservés</p>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </body>
        </html>
        """.formatted(uiBaseUrl, linkPath, COPYRIGHT_DATE);

        String textContent = """
        Réinitialisation du mot de passe

        Vous avez demandé la réinitialisation de votre mot de passe.
        Utilisez le lien ci-dessous pour en choisir un nouveau :

        %s/%s

        Ce lien expirera dans une heure.

        Si vous n'êtes pas à l'origine de cette demande, ignorez cet e-mail.
        """.formatted(uiBaseUrl, linkPath);

        return new EmailBody(htmlContent, textContent);
    }

    public EmailBody getPasswordChangeConfirmation() {
        String htmlContent = """
        <!DOCTYPE html>
        <html>
        <body style="margin:0; padding:0; background-color:#f4f6f8;">
          <table width="100%%" cellpadding="0" cellspacing="0" style="padding: 40px 0;">
            <tr>
              <td align="center">
                <table width="480" cellpadding="0" cellspacing="0" style="background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.06);">
                  <tr>
                    <td style="background:#1B4F72; padding:28px; text-align:center;">
                      <h1 style="color:#ffffff; margin:0; font-size:22px;">EduSysPro</h1>
                    </td>
                  </tr>
                  <tr>
                    <td style="padding:32px;">
                      <h2 style="color:#1B4F72; margin-top:0;">Mot de passe modifié</h2>
                      <p style="color:#333333; line-height:1.6; font-size:15px;">
                        Bonjour %s,
                      </p>
                      <p style="color:#333333; line-height:1.6; font-size:15px;">
                        Nous vous confirmons que le mot de passe de votre compte a bien été modifié.
                      </p>
                      <div style="text-align:center; margin:28px 0;">
                        <a href="%s/login" style="background:#1B4F72; color:#ffffff; text-decoration:none; padding:12px 28px; border-radius:6px; font-size:14px; display:inline-block;">
                          Se connecter
                        </a>
                      </div>
                      <p style="color:#999999; font-size:12px; margin-top:32px; border-top:1px solid #eeeeee; padding-top:16px;">
                        Si vous n'êtes pas à l'origine de cette modification, contactez immédiatement notre support à
                        <a href="%s" style="color:#1B4F72;">support@edusyspro.com</a>.
                      </p>
                    </td>
                  </tr>
                  <tr>
                    <td style="background:#f4f6f8; padding:16px; text-align:center;">
                      <p style="color:#999999; font-size:12px; margin:0;">© %d EduSysPro — Tous droits réservés</p>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </body>
        </html>
        """.formatted(userInfo.getUsername(), uiBaseUrl, supportEmail, COPYRIGHT_DATE);

        String textContent = """
        Mot de passe modifié

        Bonjour %s,

        Nous vous confirmons que le mot de passe de votre compte a bien été modifié.

        Si vous n'êtes pas à l'origine de cette modification, contactez immédiatement notre support :
        support@edusyspro.com
        """.formatted(userInfo.getUsername());
        return new EmailBody(htmlContent, textContent);
    }

    public record EmailBody(String html, String text) {
    }
}
