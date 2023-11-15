package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.view.UserNotActivatedViewModel;

import java.util.List;

public interface UserService {

    void register(UserRegistrationBindingModel userRegistrationBindingModel);

    boolean findIfEmailExist(String email);

    List<UserNotActivatedViewModel> getAllNotActivated();
}
