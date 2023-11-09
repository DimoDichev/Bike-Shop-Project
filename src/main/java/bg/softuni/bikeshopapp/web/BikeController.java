package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.service.BikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bikes")
public class BikeController {

    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
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

}
