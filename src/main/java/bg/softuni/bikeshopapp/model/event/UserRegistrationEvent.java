package bg.softuni.bikeshopapp.model.event;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    private UserEntity userEntity;
    private String appUrl;

    public UserRegistrationEvent(UserEntity user, String appUrl) {
        super(user);
        this.userEntity = user;
        this.appUrl = appUrl;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public String getAppUrl() {
        return appUrl;
    }
}
