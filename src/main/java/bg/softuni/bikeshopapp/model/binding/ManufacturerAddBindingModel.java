package bg.softuni.bikeshopapp.model.binding;

import jakarta.validation.constraints.NotEmpty;

public class ManufacturerAddBindingModel {

    @NotEmpty(message = "You should provide a manufacturer name!")
    private String name;

    public ManufacturerAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public ManufacturerAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }


}
