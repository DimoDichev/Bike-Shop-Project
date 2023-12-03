package bg.softuni.bikeshopapp.model.view;

public class ContactUsView {

    private Long id;

    private String email;

    private String message;

    public ContactUsView() {
    }

    public Long getId() {
        return id;
    }

    public ContactUsView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactUsView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ContactUsView setMessage(String message) {
        this.message = message;
        return this;
    }
}
