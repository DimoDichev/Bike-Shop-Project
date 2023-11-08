package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.enums.ModelCategoryEnum;
import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.repository.BikeRepository;
import bg.softuni.bikeshopapp.service.BikeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {

    private final BikeRepository bikeRepository;

    public BikeServiceImpl(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public List<BikeBaseViewModel> findByCategories(String category) {
        return bikeRepository
                .findByModel_CategoryOrderById(ModelCategoryEnum.valueOf(category))
                .stream()
                .map(bikeEntity -> new BikeBaseViewModel()
                        .setManufacturer(bikeEntity.getModel().getManufacturer().getName())
                        .setModel(bikeEntity.getModel().getName())
                        .setImageUrl(bikeEntity.getImageUrl().stream().findFirst().get().getUrl()))
                .toList();
    }

    @Override
    public List<BikeBaseViewModel> getAllBikes() {
        return bikeRepository
                .findAll()
                .stream()
                .map(bikeEntity -> new BikeBaseViewModel()
                        .setManufacturer(bikeEntity.getModel().getManufacturer().getName())
                        .setModel(bikeEntity.getModel().getName())
                        .setImageUrl(bikeEntity.getImageUrl().stream().findFirst().get().getUrl()))
                .toList();
    }
}
