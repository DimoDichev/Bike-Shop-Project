package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.entity.UserEntity;

public interface EmailService {
    void sendRegistrationEmail(UserEntity user, String url);

    void sendTempPassword(String email);

    void sendAnswer(Long id, String answer);
}
