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

@Controller
@RequestMapping("/users")
public class UserAuthenticationController {

    private final UserService userService;

    public UserAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "user-register";
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
        return "user-login";
    }

    @PostMapping("/login-error")
    public String onFailure(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", true);
        return "user-login";
    }

    @PostMapping("/activation/{id}")
    public String activateUsers(@PathVariable Long id) {
        userService.activateUser(id);
        return "redirect:/users/profiles/" + id;
    }

    @PostMapping("/deactivation/{id}")
    public String deactivateUsers(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        boolean successful = userService.deactivateUser(id);

        if (!successful) {
            redirectAttributes.addFlashAttribute("errorDisables", true);
            return "redirect:/users/profiles/edit/" + id;
        }

        return "redirect:/users/profiles/" + id;
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
