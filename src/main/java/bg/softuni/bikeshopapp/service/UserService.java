package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.AppUserDetails;
import bg.softuni.bikeshopapp.model.binding.UserEditNamesBindingModel;
import bg.softuni.bikeshopapp.model.binding.UserEditPasswordBindingModel;
import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.model.view.UserViewModel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    void register(UserRegistrationBindingModel userRegistrationBindingModel, HttpServletRequest request);

    boolean deleteUser(Long id);

    void activateUser(Long id);
    boolean deactivateUser(Long id);

    void editNames(UserEditNamesBindingModel userEditNamesBindingModel);

    boolean changeRole(Long id, String userRole);

    void changePassword(UserEditPasswordBindingModel userEditPasswordBindingModel);

    boolean findIfEmailExist(String email);

    UserViewModel getUserProfile(Long id);

    List<UserBaseViewModel> getAllUsers();

    boolean checkPromiseToEdit(AppUserDetails currentUser, Long editUserId);

    void saveToken(UserEntity user, String token);

    void validateToken(String token);

    boolean checkActivationStatus(String email);
}
