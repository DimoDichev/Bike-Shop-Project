package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.service.UserService;
import bg.softuni.bikeshopapp.service.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserAuthenticationController {

    private final UserService userService;
    private final VerificationService verificationService;

    public UserAuthenticationController(UserService userService, VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @GetMapping("/login")
    public String login() {
        return "user-login";
    }

    @PostMapping("/login-error")
    public String onFailure(@ModelAttribute("email") String email, Model model, HttpServletRequest request) {
        boolean emailExist = userService.findIfEmailExist(email);
        boolean isActivate = userService.checkActivationStatus(email);
        boolean tokenExpired = verificationService.checkForToken(email);

        if (emailExist && !isActivate && tokenExpired) {
            verificationService.reSendVerifyEmail(email, request);
            model.addAttribute("resendToken", true);
            return "user-login";
        }

        if (emailExist && !isActivate) {
            model.addAttribute("notActivated", true);
            return "user-login";
        }

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
    public UserBaseViewModel userBaseViewModel() {
        return new UserBaseViewModel();
    }
}
