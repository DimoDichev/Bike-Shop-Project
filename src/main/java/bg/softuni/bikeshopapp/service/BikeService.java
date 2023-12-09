package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.binding.BikeAddBindingModel;
import bg.softuni.bikeshopapp.model.binding.UploadPicturesBindingModel;
import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.model.view.BikeDetailsViewModel;
import bg.softuni.bikeshopapp.model.view.BikePictureViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BikeService {
    List<BikeBaseViewModel> findByCategories(String category);
    List<BikeBaseViewModel> getAllBikes();
    BikeDetailsViewModel getBikeDetailsById(Long id);
    void save(BikeAddBindingModel bikeAddBindingModel);

    void deleteBike(Long id) throws IOException;

    void updateBikePictures(UploadPicturesBindingModel uploadPicturesBindingModel) throws IOException;
}
