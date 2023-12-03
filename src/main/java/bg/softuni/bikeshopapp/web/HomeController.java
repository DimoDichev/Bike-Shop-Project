package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.binding.ContactUsBindingModel;
import bg.softuni.bikeshopapp.service.ContactUsService;
import bg.softuni.bikeshopapp.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final ContactUsService contactUsService;
    private final EmailService emailService;

    public HomeController(ContactUsService contactUsService, EmailService emailService) {
        this.contactUsService = contactUsService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactUs() {
        return "contact";
    }

    @PostMapping("/contact")
    public String contactUs(@Valid ContactUsBindingModel contactUsBindingModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contactUsBindingModel", contactUsBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.contactUsBindingModel", bindingResult);
            return "redirect:contact";
        }

        contactUsService.send(contactUsBindingModel);

        return "redirect:/";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgot-pass";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email) {
        emailService.sendTempPassword(email);
        return "redirect:/";
    }

    @ModelAttribute
    public ContactUsBindingModel contactUsBindingModel() {
        return new ContactUsBindingModel();
    }

}
