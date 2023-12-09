package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.exception.ForbiddenException;
import bg.softuni.bikeshopapp.model.AppUserDetails;
import bg.softuni.bikeshopapp.model.binding.UserEditNamesBindingModel;
import bg.softuni.bikeshopapp.model.binding.UserEditPasswordBindingModel;
import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.bikeshopapp.exception.ErrorMessages.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profiles")
    public String profileDetails(Model model) {
        List<UserBaseViewModel> allUser = userService.getAllUsers();
        model.addAttribute("allUsers", allUser);
        return "users-profiles";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal AppUserDetails loggedUser) {

        return "redirect:/users/profiles/" + loggedUser.getId();
    }

    @GetMapping("/profiles/{id}")
    public String profileDetails(@AuthenticationPrincipal AppUserDetails loggedUser,
                                 @PathVariable Long id,
                                 Model model) {
        if (!userService.checkPromiseToEdit(loggedUser, id)) {
            throw new ForbiddenException(FORBIDDEN);
        }

        model.addAttribute("userProfile", userService.getUserProfile(id));
        return "user-profile-details";
    }

    @GetMapping("/profiles/edit/{id}")
    public String profileEdit(@PathVariable Long id, Model model) {
        model.addAttribute("userProfile", userService.getUserProfile(id));
        return "user-profile-edit";
    }

    @PostMapping("/profiles/edit/names/{id}")
    public String editUserNames(@PathVariable Long id, @Valid UserEditNamesBindingModel userEditNamesBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditNamesBindingModel", userEditNamesBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userEditNamesBindingModel", bindingResult);
            return "redirect:/users/profiles/edit/" + id;
        }

        userService.editNames(userEditNamesBindingModel);

        return "redirect:/users/profiles/" + id;
    }

    @PostMapping("/profiles/edit/password/{id}")
    public String editUserPassword(@AuthenticationPrincipal AppUserDetails currentUser,
                                   @PathVariable Long id,
                                   @Valid UserEditPasswordBindingModel userEditPasswordBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditPasswordBindingModel", userEditPasswordBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userEditPasswordBindingModel", bindingResult);
            return "redirect:/users/profiles/edit/" + id;
        }

        boolean promiseToEdit = userService.checkPromiseToEdit(currentUser, id);

        if (!promiseToEdit) {
            throw new ForbiddenException(FORBIDDEN);
        }

        boolean passwordNotEquals =
                !userEditPasswordBindingModel.getNewPassword()
                        .equals(userEditPasswordBindingModel.getConfirmNewPassword());

        if (passwordNotEquals) {
            redirectAttributes.addFlashAttribute("passwordNotEquals", true);
            return "redirect:/users/profiles/edit/" + id;
        }

        userService.changePassword(userEditPasswordBindingModel);

        return "redirect:/users/profiles/" + id;
    }

    @PostMapping("/profiles/edit/role/{id}")
    public String editUserRole(@PathVariable Long id,
                               @RequestParam String userRole,
                               RedirectAttributes redirectAttributes) {
        boolean successfully = userService.changeRole(id, userRole);

        if (!successfully) {
           redirectAttributes.addFlashAttribute("errorChangeRole", true);
            return "redirect:/users/profiles/edit/" + id;
        }

        return "redirect:/users/profiles/" + id;
    }

    @DeleteMapping("/profiles/edit/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {

        boolean successfully = userService.deleteUser(id);

        if (!successfully) {
            redirectAttributes.addFlashAttribute("errorDelete", true);
            return "redirect:/users/profiles/edit/" + id;
        }

        return "redirect:/users/profiles";
    }

    @ModelAttribute
    public UserRegistrationBindingModel userRegistrationBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @ModelAttribute
    public UserBaseViewModel userBaseViewModel() {
        return new UserBaseViewModel();
    }

    @ModelAttribute
    public UserEditNamesBindingModel userEditNamesBindingModel() {
        return new UserEditNamesBindingModel();
    }

    @ModelAttribute
    public UserEditPasswordBindingModel userEditPasswordBindingModel() {
        return new UserEditPasswordBindingModel();
    }

}
