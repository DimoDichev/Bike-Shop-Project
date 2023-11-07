package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;

public interface UserService {

    void register(UserRegistrationBindingModel userRegistrationBindingModel);

    boolean findIfEmailExist(String email);
}
