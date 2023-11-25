package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.binding.BikeAddBindingModel;
import bg.softuni.bikeshopapp.model.entity.BikeEntity;
import bg.softuni.bikeshopapp.model.entity.ModelEntity;
import bg.softuni.bikeshopapp.model.entity.PictureEntity;
import bg.softuni.bikeshopapp.model.enums.CategoryEnum;
import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.model.view.BikeDetailsViewModel;
import bg.softuni.bikeshopapp.repository.BikeRepository;
import bg.softuni.bikeshopapp.repository.ModelRepository;
import bg.softuni.bikeshopapp.service.BikeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {

    private final BikeRepository bikeRepository;
    private final ModelRepository modelRepository;

    public BikeServiceImpl(BikeRepository bikeRepository, ModelRepository modelRepository) {
        this.bikeRepository = bikeRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public void save(BikeAddBindingModel bikeAddBindingModel) {
        BikeEntity bike = mapBike(bikeAddBindingModel);

        bikeRepository.save(bike);
    }

    @Override
    public List<BikeBaseViewModel> getAllBikes() {
        return bikeRepository
                .findAll()
                .stream()
                .map(this::mapBaseBikeView)
                .toList();
    }

    @Override
    public List<BikeBaseViewModel> findByCategories(String category) {
        return bikeRepository
                .findByCategory(CategoryEnum.valueOf(category))
                .stream()
                .map(this::mapBaseBikeView)
                .toList();
    }

    @Override
    public BikeDetailsViewModel getBikeDetailsById(Long id) {
        return bikeRepository
                .findById(id)
                .map(this::mapDetailBikeView)
                .orElseThrow(() -> new ObjectNotFoundException("Bike with ID:" + id + " not found!"));
    }

    private BikeEntity mapBike(BikeAddBindingModel bikeAddBindingModel) {
        ModelEntity model = modelRepository
                .findById(bikeAddBindingModel.getModelId())
                .orElseThrow(() -> new ObjectNotFoundException("Model with id: " + bikeAddBindingModel.getModelId() + " was not found!"));

        return new BikeEntity()
                .setModel(model)
                .setFrameSize(bikeAddBindingModel.getFrameSize())
                .setFrameMaterial(bikeAddBindingModel.getFrameMaterial())
                .setWheelSize(bikeAddBindingModel.getWheelSize())
                .setDescription(bikeAddBindingModel.getDescription())
                .setCategory(bikeAddBindingModel.getCategory())
                .setPrice(bikeAddBindingModel.getPrice());
    }

    private BikeBaseViewModel mapBaseBikeView(BikeEntity bikeEntity) {
        Set<PictureEntity> imagesUrls = bikeEntity.getImagesUrl();

        return new BikeBaseViewModel()
                .setId(bikeEntity.getId())
                .setManufacturer(bikeEntity.getModel().getManufacturer().getName())
                .setModel(bikeEntity.getModel().getName())
                .setImageUrl(imagesUrls.isEmpty() ? "/images/bike-icon.jpg" : imagesUrls.stream().findFirst().get().getUrl());
    }

    private BikeDetailsViewModel mapDetailBikeView(BikeEntity bikeEntity) {
        return new BikeDetailsViewModel()
                .setManufacturer(bikeEntity.getModel().getManufacturer().getName())
                .setModel(bikeEntity.getModel().getName())
                .setSize(bikeEntity.getFrameSize().name())
                .setFrameMaterial(bikeEntity.getFrameMaterial().name())
                .setWheelSize(bikeEntity.getWheelSize())
                .setDescription(bikeEntity.getDescription())
                .setImagesUrl(bikeEntity
                        .getImagesUrl()
                        .stream()
                        .map(PictureEntity::getUrl)
                        .collect(Collectors.toList()))
                .setPrice(bikeEntity.getPrice());
    }
}
