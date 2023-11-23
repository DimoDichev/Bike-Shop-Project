package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.event.UserRegistrationEvent;
import bg.softuni.bikeshopapp.service.EmailService;
import bg.softuni.bikeshopapp.service.UserActivationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    private final EmailService emailService;

    public UserActivationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @EventListener(UserRegistrationEvent.class)
    public void userRegistered(UserRegistrationEvent event) {
        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUsername());
    }
}
