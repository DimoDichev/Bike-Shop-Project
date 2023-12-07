package bg.softuni.bikeshopapp.model.event;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.service.EmailService;
import bg.softuni.bikeshopapp.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserRegistrationConfirmEventListener implements ApplicationListener<UserRegistrationEvent> {

    private final UserService userService;
    private final EmailService emailService;

    public UserRegistrationConfirmEventListener(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        String token = UUID.randomUUID().toString();
        UserEntity user = event.getUserEntity();

        userService.saveToken(user, token);
        String url = event.getAppUrl()
                + "/users/register/verify?token="
                + token;

        emailService.sendRegistrationEmail(user, url);
    }
}
