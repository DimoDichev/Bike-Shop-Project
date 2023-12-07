package bg.softuni.bikeshopapp.web;

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
@RequestMapping("/models")
public class ModelController {
    private final ManufacturerService manufacturerService;
    private final ModelService modelService;

    public ModelController(ManufacturerService manufacturerService, ModelService modelService) {
        this.manufacturerService = manufacturerService;
        this.modelService = modelService;
    }

    @GetMapping("/add")
    public String addModel(Model model) {
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturerNames());
        return "model-add";
    }

    @PostMapping("/add")
    public String addModel(@Valid ModelAddBindingModel modelAddBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("modelAddBindingModel", modelAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.modelAddBindingModel", bindingResult);
            return "redirect:/models/add";
        }

        if(modelService.findIfModelExist(modelAddBindingModel.getName())) {
            redirectAttributes.addFlashAttribute("modelAddBindingModel", modelAddBindingModel)
                    .addFlashAttribute("alreadyExist", true);
            return "redirect:/models/add";
        }

        modelService.save(modelAddBindingModel);

        return "redirect:/admin/panel";
    }

    @ModelAttribute
    public ModelAddBindingModel modelAddBindingModel() {
        return new ModelAddBindingModel();
    }

}
