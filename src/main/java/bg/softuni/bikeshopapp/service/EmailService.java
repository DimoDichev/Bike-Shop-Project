package bg.softuni.bikeshopapp.service;

public interface EmailService {
    void sendRegistrationEmail(String email, String username);

    void sendTempPassword(String email);

    void sendAnswer(Long id, String answer);
}
