package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String bikeShopEmail;

    public EmailServiceImpl(
            TemplateEngine templateEngine,
            JavaMailSender javaMailSender,
            @Value("${mail.bikeshop}") String bikeShopEmail) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.bikeShopEmail = bikeShopEmail;
    }

    @Override
    public void sendRegistrationEmail(String userEmail, String fullName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(bikeShopEmail);
            mimeMessageHelper.setReplyTo(bikeShopEmail);
            mimeMessageHelper.setSubject("Welcome to Bike Shop");
            mimeMessageHelper.setText(generateRegistrationEmailBody(fullName), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateRegistrationEmailBody(String fullName) {
        Context context = new Context();
        context.setVariable("fullName", fullName);

        return templateEngine.process("email/registration-email", context);
    }
}
