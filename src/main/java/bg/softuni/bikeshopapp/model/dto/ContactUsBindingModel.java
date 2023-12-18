package bg.softuni.bikeshopapp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ContactUsBindingModel {
    @NotEmpty(message = "You must provide valid email!")
    @Email(message = "You must provide valid email!")
    private String email;
    @NotEmpty(message = "Message must be at least 10 characters!")
    @Size(min = 10, message = "Message must be at least 10 characters!")
    private String message;

    public ContactUsBindingModel() {
    }

    public String getEmail() {
        return email;
    }

    public ContactUsBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ContactUsBindingModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
