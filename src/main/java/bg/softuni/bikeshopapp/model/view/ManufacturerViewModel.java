package bg.softuni.bikeshopapp.model.view;

import java.util.List;

public class ManufacturerViewModel {

    private String name;
    private List<ModelViewModel> models;

    public ManufacturerViewModel() {
    }

    public String getName() {
        return name;
    }

    public ManufacturerViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<ModelViewModel> getModels() {
        return models;
    }

    public ManufacturerViewModel setModels(List<ModelViewModel> models) {
        this.models = models;
        return this;
    }
}
