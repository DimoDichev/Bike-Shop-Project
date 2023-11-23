package bg.softuni.bikeshopapp.service;

public interface EmailService {
    void sendRegistrationEmail(String email, String username);
}
