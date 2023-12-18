package bg.softuni.bikeshopapp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {
    @NotEmpty
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters!")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters!")
    private String lastName;
    @NotNull
    @Email(message = "You must provide valid email!")
    private String email;
    @NotNull
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters!")
    private String password;
    @NotNull
    @Size(min = 8, max = 40, message = "Confirm password must be equal to password!")
    private String confirmPassword;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String firstName, String lastName, String email, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}