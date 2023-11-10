package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.entity.ManufacturerEntity;
import bg.softuni.bikeshopapp.model.view.ManufacturerNameViewModel;
import bg.softuni.bikeshopapp.model.view.ManufacturerViewModel;
import bg.softuni.bikeshopapp.model.view.ModelViewModel;
import bg.softuni.bikeshopapp.repository.ManufacturerRepository;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<ManufacturerViewModel> getAllManufacturersWithModels() {
        return manufacturerRepository
                .findAll()
                .stream()
                .map(manufacturer -> new ManufacturerViewModel()
                        .setName(manufacturer.getName())
                        .setModels(manufacturer.getModels()
                                .stream()
                                .map(model -> new ModelViewModel()
                                        .setId(model.getId())
                                        .setName(model.getName()))
                                .sorted(Comparator.comparing(ModelViewModel::getName))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManufacturerNameViewModel> getAllManufacturerNames() {
        return manufacturerRepository
                .findAll()
                .stream()
                .map(manufacturer -> new ManufacturerNameViewModel()
                        .setId(manufacturer.getId())
                        .setName(manufacturer.getName()))
                .sorted(Comparator.comparing(ManufacturerNameViewModel::getName))
                .collect(Collectors.toList());
    }

    @Override
    public void save(String name) {
        manufacturerRepository.save(new ManufacturerEntity().setName(name));
    }

    @Override
    public boolean findIfManufacturerExist(String name) {
        return manufacturerRepository.findByName(name).orElse(null) != null;
    }
}
