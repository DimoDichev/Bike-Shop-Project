package bg.softuni.bikeshopapp.model.entity;

import bg.softuni.bikeshopapp.model.enums.CategoryEnum;
import bg.softuni.bikeshopapp.model.enums.FrameMaterialEnum;
import bg.softuni.bikeshopapp.model.enums.FrameSizeEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "bikes")
public class BikeEntity extends BaseEntity {

    @ManyToOne
    private ModelEntity model;

    @Enumerated(EnumType.STRING)
    private FrameSizeEnum frameSize;

    @Enumerated(EnumType.STRING)
    private FrameMaterialEnum frameMaterial;

    @Column(nullable = false)
    private String wheelSize;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<PictureEntity> imagesUrl;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Column()
    private Double price;

    public BikeEntity() {
    }

    public ModelEntity getModel() {
        return model;
    }

    public BikeEntity setModel(ModelEntity model) {
        this.model = model;
        return this;
    }

    public FrameSizeEnum getFrameSize() {
        return frameSize;
    }

    public BikeEntity setFrameSize(FrameSizeEnum frameSize) {
        this.frameSize = frameSize;
        return this;
    }

    public FrameMaterialEnum getFrameMaterial() {
        return frameMaterial;
    }

    public BikeEntity setFrameMaterial(FrameMaterialEnum frameMaterial) {
        this.frameMaterial = frameMaterial;
        return this;
    }

    public String getWheelSize() {
        return wheelSize;
    }

    public BikeEntity setWheelSize(String wheelSize) {
        this.wheelSize = wheelSize;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BikeEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<PictureEntity> getImagesUrl() {
        return imagesUrl;
    }

    public BikeEntity setImagesUrl(Set<PictureEntity> imagesUrl) {
        this.imagesUrl = imagesUrl;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public BikeEntity setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public BikeEntity setPrice(Double price) {
        this.price = price;
        return this;
    }
}