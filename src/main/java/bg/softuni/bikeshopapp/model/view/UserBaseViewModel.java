package bg.softuni.bikeshopapp.model.view;

public class UserBaseViewModel {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public UserBaseViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserBaseViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserBaseViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserBaseViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserBaseViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
