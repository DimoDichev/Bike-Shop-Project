package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;

import java.util.List;

public interface BikeService {
    List<BikeBaseViewModel> findByCategories(String category);
    List<BikeBaseViewModel> getAllBikes();
}
