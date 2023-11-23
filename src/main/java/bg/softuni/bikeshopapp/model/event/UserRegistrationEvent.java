package bg.softuni.bikeshopapp.model.event;

import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    private final String userEmail;
    private final String username;

    public UserRegistrationEvent(Object source, String userEmail, String username) {
        super(source);
        this.userEmail = userEmail;
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsername() {
        return username;
    }
}
