package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.entity.VerificationEntity;
import bg.softuni.bikeshopapp.service.UserService;
import bg.softuni.bikeshopapp.service.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;
    private final VerificationService verificationService;

    public UserRegistrationController(UserService userService, VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @GetMapping("/register")
    public String register() {
        return "user-register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationBindingModel userRegistrationBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);
            return "redirect:register";
        }

        boolean passwordNotEquals =
                !userRegistrationBindingModel.getPassword()
                        .equals(userRegistrationBindingModel.getConfirmPassword());

        if (passwordNotEquals) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel)
                    .addFlashAttribute("passwordNotEquals", true);
            return "redirect:register";
        }

        boolean emailExist = userService.findIfEmailExist(userRegistrationBindingModel.getEmail());

        if (emailExist) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel)
                    .addFlashAttribute("emailExist", true);
            return "redirect:register";
        }

        userService.register(userRegistrationBindingModel, request);

        return "redirect:login";
    }

    @GetMapping("/register/verify")
    public String verification(@RequestParam("token") String token, Model model) {
        VerificationEntity verificationToken = verificationService.findByToken(token);

        if (verificationToken == null || verificationToken.getUser().getEnabled()) {
            return "user-login";
        }

        userService.validateToken(token);
        model.addAttribute("message", true);

        return "user-login";
    }

    @ModelAttribute
    public UserRegistrationBindingModel userRegistrationBindingModel() {
        return new UserRegistrationBindingModel();
    }

}
