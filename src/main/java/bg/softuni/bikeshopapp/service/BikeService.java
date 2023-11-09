package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.model.view.BikeDetailsViewModel;

import java.util.List;

public interface BikeService {
    List<BikeBaseViewModel> findByCategories(String category);
    List<BikeBaseViewModel> getAllBikes();
    BikeDetailsViewModel getBikeDetailsById(Long id);
}
