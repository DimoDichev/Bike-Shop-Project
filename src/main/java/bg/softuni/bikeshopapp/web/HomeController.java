package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.binding.ContactUsBindingModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

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

        // TODO
        return "redirect:/";
    }

    @ModelAttribute
    public ContactUsBindingModel contactUsBindingModel() {
        return new ContactUsBindingModel();
    }

}
