package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.dto.ManufacturerAddBindingModel;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/add")
    public String addManufacturer() {
        return "manufacturer-add";
    }

    @PostMapping("/add")
    public String addManufacturer(@Valid ManufacturerAddBindingModel manufacturerAddBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("manufacturerAddBindingModel", manufacturerAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.manufacturerAddBindingModel", bindingResult);
            return "redirect:/manufacturers/add";
        }

        if(manufacturerService.findIfManufacturerExist(manufacturerAddBindingModel.getName())) {
            redirectAttributes.addFlashAttribute("manufacturerAddBindingModel", manufacturerAddBindingModel)
                    .addFlashAttribute("alreadyExist", true);
            return "redirect:/manufacturers/add";
        }

        manufacturerService.save(manufacturerAddBindingModel.getName());

        return "redirect:/admin/panel";
    }

    @ModelAttribute
    public ManufacturerAddBindingModel manufacturerAddBindingModel() {
        return new ManufacturerAddBindingModel();
    }

}
