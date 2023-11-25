package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.binding.ModelAddBindingModel;
import bg.softuni.bikeshopapp.model.entity.ModelEntity;
import bg.softuni.bikeshopapp.repository.ManufacturerRepository;
import bg.softuni.bikeshopapp.repository.ModelRepository;
import bg.softuni.bikeshopapp.service.ModelService;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ModelServiceImpl(
            ModelRepository modelRepository,
            ManufacturerRepository manufacturerRepository) {
        this.modelRepository = modelRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public boolean findIfModelExist(String name) {
        return modelRepository.findByName(name).orElse(null) != null;
    }

    @Override
    public void save(ModelAddBindingModel modelAddBindingModel) {
        modelRepository
                .save(new ModelEntity()
                        .setName(modelAddBindingModel.getName())
                        .setManufacturer(manufacturerRepository
                                .findById(modelAddBindingModel.getManufacturerId())
                                .orElseThrow(() -> new ObjectNotFoundException("Manufacturer with ID:" + modelAddBindingModel.getManufacturerId() + " not found!"))));
    }
}
