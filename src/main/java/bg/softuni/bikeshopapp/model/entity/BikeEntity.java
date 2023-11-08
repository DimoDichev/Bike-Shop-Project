package bg.softuni.bikeshopapp.model.entity;

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
    private double wheelSize;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "bike", fetch = FetchType.EAGER)
    private Set<PictureEntity> imageUrl;
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

    public double getWheelSize() {
        return wheelSize;
    }

    public BikeEntity setWheelSize(double wheelSize) {
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

    public Set<PictureEntity> getImageUrl() {
        return imageUrl;
    }

    public BikeEntity setImageUrl(Set<PictureEntity> imageUrl) {
        this.imageUrl = imageUrl;
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
