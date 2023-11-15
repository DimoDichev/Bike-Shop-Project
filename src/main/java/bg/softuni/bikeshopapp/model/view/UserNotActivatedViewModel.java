package bg.softuni.bikeshopapp.model.view;

public class UserNotActivatedViewModel {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public UserNotActivatedViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserNotActivatedViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserNotActivatedViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserNotActivatedViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserNotActivatedViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
