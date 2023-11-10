package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.view.ManufacturerNameViewModel;
import bg.softuni.bikeshopapp.model.view.ManufacturerViewModel;

import java.util.List;

public interface ManufacturerService {
    List<ManufacturerViewModel> getAllManufacturersWithModels();

    List<ManufacturerNameViewModel> getAllManufacturerNames();

    boolean findIfManufacturerExist(String name);

    void save(String name);
}
