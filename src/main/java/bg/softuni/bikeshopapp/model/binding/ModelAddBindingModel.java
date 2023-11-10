package bg.softuni.bikeshopapp.model.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ModelAddBindingModel {

    @NotEmpty(message = "You should provide a model name!")
    private String name;
    @NotNull(message = "You should select a manufacturer!")
    private Long manufacturerId;

    public ModelAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public ModelAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public ModelAddBindingModel setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
        return this;
    }
}
