package bg.softuni.bikeshopapp.model.view;

import java.util.Set;

public class UserViewModel {

    private Long id;
    private String email;
    private String firstName;
    private String lastname;
    private Boolean enabled;
    private String userRole;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserViewModel setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public UserViewModel setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public UserViewModel setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }
}
