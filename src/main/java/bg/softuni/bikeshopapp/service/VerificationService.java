package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.entity.VerificationEntity;
import jakarta.servlet.http.HttpServletRequest;

public interface VerificationService {
    VerificationEntity findByToken(String token);

    void cleanUp();

    void reSendVerifyEmail(String email, HttpServletRequest request);

    boolean checkForToken(String email);
}