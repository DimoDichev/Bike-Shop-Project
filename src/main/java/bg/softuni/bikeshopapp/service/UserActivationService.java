package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.event.UserRegistrationEvent;

public interface UserActivationService {

    void userRegistered(UserRegistrationEvent event);

}
