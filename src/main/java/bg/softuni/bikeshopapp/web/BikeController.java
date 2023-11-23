package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.service.BikeService;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bikes")
public class BikeController {

    private final BikeService bikeService;
    private final ManufacturerService manufacturerService;

    public BikeController(BikeService bikeService, ManufacturerService manufacturerService) {
        this.bikeService = bikeService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/{category}")
    public String viewByCategories(@PathVariable String category, Model model) {
        model.addAttribute("bikesBaseView", bikeService.findByCategories(category));
        return "bikes-view";
    }

    @GetMapping("/all")
    public String viewAll(Model model) {
        model.addAttribute("bikesBaseView", bikeService.getAllBikes());
        return "bikes-view";
    }

    @GetMapping("/details/{id}")
    public String viewBikeDetails(@PathVariable Long id, Model model) {
        model.addAttribute("bikeDetails", bikeService.getBikeDetailsById(id));
        return "bike-details";
    }

    @GetMapping("/add")
    public String addBike(Model model) {
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturersWithModels());
        return "add-bike";
    }

}
