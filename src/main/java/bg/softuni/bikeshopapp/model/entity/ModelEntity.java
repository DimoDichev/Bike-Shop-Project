package bg.softuni.bikeshopapp.model.entity;

import bg.softuni.bikeshopapp.model.enums.ModelCategoryEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    private ManufacturerEntity manufacturer;
    @Enumerated(EnumType.STRING)
    private ModelCategoryEnum category;

    public ModelEntity() {
    }

    public String getName() {
        return name;
    }

    public ModelEntity setName(String name) {
        this.name = name;
        return this;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public ModelEntity setManufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public ModelCategoryEnum getCategory() {
        return category;
    }

    public ModelEntity setCategory(ModelCategoryEnum category) {
        this.category = category;
        return this;
    }
}
