package bg.softuni.bikeshopapp.model.entity;

import bg.softuni.bikeshopapp.model.enums.ComponentsTypeEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "components")
public class ComponentEntity extends BaseEntity {

    @ManyToOne
    private ModelEntity model;

    @Enumerated(EnumType.STRING)
    private ComponentsTypeEnum type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<PictureEntity> imagesUrl;

    @Column()
    private Double price;

    public ComponentEntity() {
    }

    public ModelEntity getModel() {
        return model;
    }

    public ComponentEntity setModel(ModelEntity model) {
        this.model = model;
        return this;
    }

    public ComponentsTypeEnum getType() {
        return type;
    }

    public ComponentEntity setType(ComponentsTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ComponentEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<PictureEntity> getImagesUrl() {
        return imagesUrl;
    }

    public ComponentEntity setImagesUrl(Set<PictureEntity> imagesUrl) {
        this.imagesUrl = imagesUrl;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ComponentEntity setPrice(Double price) {
        this.price = price;
        return this;
    }
}
