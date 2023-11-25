package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationBindingModel userRegistrationBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);
            return "redirect:register";
        }

        boolean passwordNotEquals =
                !userRegistrationBindingModel.getPassword()
                        .equals(userRegistrationBindingModel.getConfirmPassword());

        if (passwordNotEquals) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel)
                    .addFlashAttribute("passwordNotEquals", true);
            return "redirect:register";
        }

        boolean emailExist = userService.findIfEmailExist(userRegistrationBindingModel.getEmail());

        if (emailExist) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel)
                    .addFlashAttribute("emailExist", true);
            return "redirect:register";
        }

        userService.register(userRegistrationBindingModel);

        return "redirect:login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailure(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", true);
        return "login";
    }

    @GetMapping("/activations")
    public String activateUsers(Model model) {
        List<UserBaseViewModel> notActivated = userService.getAllNotActivated();
        model.addAttribute("notActivated", notActivated);
        return "users-activations";
    }

    @GetMapping("/activations/{id}")
    public String activateUsers(@PathVariable Long id) {
        userService.activateUser(id);
        return "redirect:/users/activations";
    }

    @GetMapping("/edit")
    public String editUser(Model model) {
        List<UserBaseViewModel> allUser = userService.getAllUsers();
        model.addAttribute("notActivated", allUser);
        return "users-edit";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("userProfile", userService.getUserProfile(id));
        return "user-profile-edit";
    }

    @DeleteMapping("/edit/{id}")
    public String deleteUser(@PathVariable Long id) {
        // TODO: Show error for deleting last admin
        userService.deleteUser(id);
        return "redirect:/users/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUserRole(@PathVariable Long id, @RequestParam String userRole) {
        // TODO: Show error for changes last admin roles
        userService.changeRole(id, userRole);
        return "redirect:/users/edit";
    }

    @ModelAttribute
    public UserRegistrationBindingModel userRegistrationBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @ModelAttribute
    public UserBaseViewModel userBaseViewModel() {
        return new UserBaseViewModel();
    }

}
