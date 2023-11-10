package bg.softuni.bikeshopapp.model.binding;

import bg.softuni.bikeshopapp.model.entity.PictureEntity;
import bg.softuni.bikeshopapp.model.enums.CategoryEnum;
import bg.softuni.bikeshopapp.model.enums.FrameMaterialEnum;
import bg.softuni.bikeshopapp.model.enums.FrameSizeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BikeAddBindingModel {

    @NotNull(message = "You must select a model!")
    private Long modelId;
    @NotEmpty(message = "You must select a size!")
    private FrameSizeEnum frameSize;
    @NotEmpty(message = "You must select a frame material!")
    private FrameMaterialEnum frameMaterial;
    @NotEmpty(message = "You must provide a wheel size!")
    private String wheelSize;
    private String description;
    private PictureEntity imageUrl;
    @NotNull(message = "You must select a category!")
    private CategoryEnum category;
    private Double price;

    public BikeAddBindingModel() {
    }

    public Long getModelId() {
        return modelId;
    }

    public BikeAddBindingModel setModelId(Long modelId) {
        this.modelId = modelId;
        return this;
    }

    public FrameSizeEnum getFrameSize() {
        return frameSize;
    }

    public BikeAddBindingModel setFrameSize(FrameSizeEnum frameSize) {
        this.frameSize = frameSize;
        return this;
    }

    public FrameMaterialEnum getFrameMaterial() {
        return frameMaterial;
    }

    public BikeAddBindingModel setFrameMaterial(FrameMaterialEnum frameMaterial) {
        this.frameMaterial = frameMaterial;
        return this;
    }

    public String getWheelSize() {
        return wheelSize;
    }

    public BikeAddBindingModel setWheelSize(String wheelSize) {
        this.wheelSize = wheelSize;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BikeAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getImageUrl() {
        return imageUrl;
    }

    public BikeAddBindingModel setImageUrl(PictureEntity imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public BikeAddBindingModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public BikeAddBindingModel setPrice(Double price) {
        this.price = price;
        return this;
    }
}
