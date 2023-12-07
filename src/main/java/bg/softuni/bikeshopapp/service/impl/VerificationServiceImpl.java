package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.VerificationEntity;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.VerificationRepository;
import bg.softuni.bikeshopapp.service.EmailService;
import bg.softuni.bikeshopapp.service.UserService;
import bg.softuni.bikeshopapp.service.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final EmailService emailService;

    public VerificationServiceImpl(VerificationRepository verificationRepository, UserRepository userRepository, UserService userService, EmailService emailService) {
        this.verificationRepository = verificationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public VerificationEntity findByToken(String token) {
        return verificationRepository.findByToken(token);
    }

    @Override
    public void cleanUp() {
        verificationRepository.deleteAll();
    }

    @Override
    public void reSendVerifyEmail(String email, HttpServletRequest request) {
        UserEntity user = userRepository
                .findByEmail(email)
                .orElse(null);

        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.saveToken(user, token);

            String url = appUrl(request)
                    + "/users/register/verify?token="
                    + token;

            emailService.sendRegistrationEmail(user, url);
        }
    }

    @Override
    public boolean checkForToken(String email) {
        return verificationRepository.findByUser_Email(email) == null;
    }

    private String appUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath();
    }
}
