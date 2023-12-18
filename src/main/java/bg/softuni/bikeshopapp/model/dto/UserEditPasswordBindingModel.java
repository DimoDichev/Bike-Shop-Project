package bg.softuni.bikeshopapp.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserEditPasswordBindingModel {

    private Long id;

    @NotNull
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters!")
    private String newPassword;

    @NotNull
    @Size(min = 8, max = 40, message = "Confirm password must be equal to new password!")
    private String confirmNewPassword;

    public UserEditPasswordBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public UserEditPasswordBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserEditPasswordBindingModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public UserEditPasswordBindingModel setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
