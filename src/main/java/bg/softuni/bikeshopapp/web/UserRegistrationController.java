package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.dto.UserRegistrationDto;
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
    public String register(@Valid UserRegistrationDto userRegistrationDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);
            return "redirect:register";
        }

        boolean passwordNotEquals =
                !userRegistrationDto.getPassword()
                        .equals(userRegistrationDto.getConfirmPassword());

        if (passwordNotEquals) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationDto)
                    .addFlashAttribute("passwordNotEquals", true);
            return "redirect:register";
        }

        boolean emailExist = userService.findIfEmailExist(userRegistrationDto.getEmail());

        if (emailExist) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationDto)
                    .addFlashAttribute("emailExist", true);
            return "redirect:register";
        }

        userService.register(userRegistrationDto, request);

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
    public UserRegistrationDto userRegistrationBindingModel() {
        return new UserRegistrationDto();
    }

}
