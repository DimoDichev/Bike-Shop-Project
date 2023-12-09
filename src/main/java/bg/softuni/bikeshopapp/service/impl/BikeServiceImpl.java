package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.binding.BikeAddBindingModel;
import bg.softuni.bikeshopapp.model.binding.UploadPicturesBindingModel;
import bg.softuni.bikeshopapp.model.entity.BikeEntity;
import bg.softuni.bikeshopapp.model.entity.ModelEntity;
import bg.softuni.bikeshopapp.model.entity.PictureEntity;
import bg.softuni.bikeshopapp.model.enums.CategoryEnum;
import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.model.view.BikeDetailsViewModel;
import bg.softuni.bikeshopapp.repository.BikeRepository;
import bg.softuni.bikeshopapp.repository.ModelRepository;
import bg.softuni.bikeshopapp.repository.PictureRepository;
import bg.softuni.bikeshopapp.service.BikeService;
import bg.softuni.bikeshopapp.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.bikeshopapp.exception.ErrorMessages.*;

@Service
public class BikeServiceImpl implements BikeService {

    private final BikeRepository bikeRepository;
    private final ModelRepository modelRepository;
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public BikeServiceImpl(BikeRepository bikeRepository, ModelRepository modelRepository, CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.bikeRepository = bikeRepository;
        this.modelRepository = modelRepository;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void save(BikeAddBindingModel bikeAddBindingModel) {
        BikeEntity bike = mapBike(bikeAddBindingModel);
        bikeRepository.save(bike);
    }

    @Override
    public void deleteBike(Long id) throws IOException {
        BikeEntity bike = bikeRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(OBJECT_NOT_FOUND, id)));
        Set<PictureEntity> imagesUrl = bike.getImagesUrl();

        for (PictureEntity picture : imagesUrl) {
            String pictureName = getPictureName(picture.getUrl());
            cloudinaryService.delete(pictureName);
        }

        bikeRepository.deleteById(id);
        pictureRepository.deleteAll(imagesUrl);
    }

    @Override
    public void updateBikePictures(UploadPicturesBindingModel uploadPicturesBindingModel) throws IOException {
        MultipartFile pictures = uploadPicturesBindingModel.getPictures();
        String imgUrl = cloudinaryService.uploadImg(pictures);
        Long bikeId = uploadPicturesBindingModel.getId();

        BikeEntity bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(OBJECT_NOT_FOUND, bikeId)));

        PictureEntity picture = new PictureEntity()
                .setUrl(imgUrl);
        pictureRepository.save(picture);

        bike.getImagesUrl().add(picture);
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
                .setId(bikeEntity.getId())
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

    private String getPictureName(String url) {
        int startIndex = url.lastIndexOf("/") + 1;
        int ednIndex = url.lastIndexOf(".") + 1;
        return url.substring(startIndex, ednIndex);
    }
}
