package bg.softuni.bikeshopapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "manufacturers")
public class ManufacturerEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "manufacturer")
    private Set<ModelEntity> models;

    public ManufacturerEntity() {
    }

    public String getName() {
        return name;
    }

    public ManufacturerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Set<ModelEntity> getModels() {
        return models;
    }

    public ManufacturerEntity setModels(Set<ModelEntity> models) {
        this.models = models;
        return this;
    }
}
