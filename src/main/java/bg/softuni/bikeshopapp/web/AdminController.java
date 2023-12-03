package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.service.ContactUsService;
import bg.softuni.bikeshopapp.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ContactUsService contactUsService;
    private final EmailService emailService;

    public AdminController(ContactUsService contactUsService, EmailService emailService) {
        this.contactUsService = contactUsService;
        this.emailService = emailService;
    }


    @GetMapping("/panel")
    public String adminPanel(Model model) {
        model.addAttribute("msgCount", contactUsService.countMsg());
        return "admin-panel";
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("allMsg", contactUsService.allMsg());
        return "messages";
    }

    @GetMapping("/messages/{id}")
    public String message(Model model, @PathVariable Long id) {
        model.addAttribute("message", contactUsService.getMessage(id));
        return "message-details";
    }

    @PostMapping("/messages/answer/{id}")
    public String answer(@PathVariable Long id,
                         @ModelAttribute("answer") String answer) {

        emailService.sendAnswer(id, answer);
        return "redirect:/admin/messages";
    }

    @DeleteMapping("/messages/delete/{id}")
    public String delete(@PathVariable Long id) {
        contactUsService.delete(id);
        return "redirect:/admin/messages";
    }

}
