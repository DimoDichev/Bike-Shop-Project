package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.model.view.UserViewModel;

import java.util.List;

public interface UserService {

    void register(UserRegistrationBindingModel userRegistrationBindingModel);

    void activateUser(Long id);

    void changeRole(Long id, String userRole);

    void deleteUser(Long id);

    boolean findIfEmailExist(String email);

    UserViewModel getUserProfile(Long id);

    List<UserBaseViewModel> getAllNotActivated();

    List<UserBaseViewModel> getAllUsers();
}
