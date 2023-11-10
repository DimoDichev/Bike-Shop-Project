package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.binding.ManufacturerAddBindingModel;
import bg.softuni.bikeshopapp.model.binding.ModelAddBindingModel;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import bg.softuni.bikeshopapp.service.ModelService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/add")
public class AdminAddController {

    private final ManufacturerService manufacturerService;
    private final ModelService modelService;

    public AdminAddController(ManufacturerService manufacturerService, ModelService modelService) {
        this.manufacturerService = manufacturerService;
        this.modelService = modelService;
    }

    @GetMapping("/manufacturer")
    public String addManufacturer() {
        return "add-manufacturer";
    }

    @PostMapping("/manufacturer")
    public String addManufacturer(@Valid ManufacturerAddBindingModel manufacturerAddBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("manufacturerAddBindingModel", manufacturerAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.manufacturerAddBindingModel", bindingResult);
            return "redirect:/admin/add/manufacturer";
        }

        if(manufacturerService.findIfManufacturerExist(manufacturerAddBindingModel.getName())) {
            redirectAttributes.addFlashAttribute("manufacturerAddBindingModel", manufacturerAddBindingModel)
                    .addFlashAttribute("alreadyExist", true);
            return "redirect:/admin/add/manufacturer";
        }

        manufacturerService.save(manufacturerAddBindingModel.getName());

        return "redirect:/admin/panel";
    }

    @GetMapping("/model")
    public String addModel(Model model) {
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturerNames());
        return "add-model";
    }

    @PostMapping("/model")
    public String addModel(@Valid ModelAddBindingModel modelAddBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("modelAddBindingModel", modelAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.modelAddBindingModel", bindingResult);
            return "redirect:/admin/add/model";
        }

        if(modelService.findIfModelExist(modelAddBindingModel.getName())) {
            redirectAttributes.addFlashAttribute("modelAddBindingModel", modelAddBindingModel)
                    .addFlashAttribute("alreadyExist", true);
            return "redirect:/admin/add/model";
        }

        modelService.save(modelAddBindingModel);

        return "redirect:/admin/panel";
    }

    @GetMapping("/bike")
    public String addBike(Model model) {
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturersWithModels());
        return "add-bike";
    }

    @ModelAttribute
    public ManufacturerAddBindingModel manufacturerAddBindingModel() {
        return new ManufacturerAddBindingModel();
    }

    @ModelAttribute
    public ModelAddBindingModel modelAddBindingModel() {
        return new ModelAddBindingModel();
    }

}
