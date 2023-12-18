package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.dto.BikeAddBindingModel;
import bg.softuni.bikeshopapp.model.dto.UploadPicturesBindingModel;
import bg.softuni.bikeshopapp.service.BikeService;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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
        return "bike-add";
    }

    @PostMapping("/add")
    public String addBike(@Valid BikeAddBindingModel bikeAddBindingModel,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("bikeAddBindingModel", bikeAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.bikeAddBindingModel", bindingResult);
            return "redirect:add";
        }

        bikeService.save(bikeAddBindingModel);

        return "redirect:/admin/panel";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBike(@PathVariable Long id) throws IOException {
        bikeService.deleteBike(id);
        return "redirect:/bikes/all";
    }

    @PostMapping("/uploadImg/{id}")
    public String uploadPicture(@PathVariable String id,
                                @Valid UploadPicturesBindingModel uploadPicturesBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("uploadPicturesBindingModel", uploadPicturesBindingModel)
                    .addFlashAttribute("errorUploadImg", true);
            return "redirect:/bikes/details/" + id;
        }

        bikeService.updateBikePictures(uploadPicturesBindingModel);

        return "redirect:/bikes/details/" + id;
    }

    @ModelAttribute
    public BikeAddBindingModel bikeAddBindingModel() {
        return new BikeAddBindingModel();
    }

    @ModelAttribute
    public UploadPicturesBindingModel uploadPicturesBindingModel() {
        return new UploadPicturesBindingModel();
    }

}
