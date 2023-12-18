package bg.softuni.bikeshopapp.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserEditNamesBindingModel {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters!")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters!")
    private String lastName;

    public UserEditNamesBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public UserEditNamesBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditNamesBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditNamesBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
