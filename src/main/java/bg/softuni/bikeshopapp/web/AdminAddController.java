package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/add")
public class AdminAddController {

    private final ManufacturerService manufacturerService;

    public AdminAddController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/bike")
    public String addBike(Model model) {
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturersWithModels());
        return "add-bike";
    }

}
