package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.entity.ContactUsEntity;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.repository.ContactUsRepository;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final ContactUsRepository contactUsRepository;
    private final String bikeShopEmail;

    public EmailServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TemplateEngine templateEngine,
            JavaMailSender javaMailSender,
            ContactUsRepository contactUsRepository,
            @Value("${mail.bikeshop}") String bikeShopEmail) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.contactUsRepository = contactUsRepository;
        this.bikeShopEmail = bikeShopEmail;
    }

    @Override
    public void sendRegistrationEmail(UserEntity user, String url) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setFrom(bikeShopEmail);
            mimeMessageHelper.setReplyTo(bikeShopEmail);
            mimeMessageHelper.setSubject("Welcome to Bike Shop");
            mimeMessageHelper.setText(generateRegistrationEmailBody(user, url), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendTempPassword(String email) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            UUID uuid = UUID.randomUUID();
            String newPassword = uuid.toString().replace("-", "").substring(0, 8);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            try {
                mimeMessageHelper.setTo(email);
                mimeMessageHelper.setFrom(bikeShopEmail);
                mimeMessageHelper.setReplyTo(bikeShopEmail);
                mimeMessageHelper.setSubject("Reset password");
                mimeMessageHelper.setText("This is your new password: " + newPassword);

                javaMailSender.send(mimeMessageHelper.getMimeMessage());

                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void sendAnswer(Long id, String answer) {
        ContactUsEntity contact = contactUsRepository.findById(id).orElse(null);

        if (contact != null) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            try {
                mimeMessageHelper.setTo(contact.getEmail());
                mimeMessageHelper.setFrom(bikeShopEmail);
                mimeMessageHelper.setReplyTo(bikeShopEmail);
                mimeMessageHelper.setSubject("Answer");
                mimeMessageHelper.setText(answer);

                javaMailSender.send(mimeMessageHelper.getMimeMessage());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String generateRegistrationEmailBody(UserEntity user, String url) {
        Context context = new Context();
        context.setVariable("name", user.getFirstName());
        context.setVariable("link", url);

        return templateEngine.process("email/registration-email", context);
    }

}
