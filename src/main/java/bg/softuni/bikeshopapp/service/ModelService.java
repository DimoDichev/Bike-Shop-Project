package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.dto.ModelAddBindingModel;

public interface ModelService {
    boolean findIfModelExist(String name);

    void save(ModelAddBindingModel modelAddBindingModel);
}
