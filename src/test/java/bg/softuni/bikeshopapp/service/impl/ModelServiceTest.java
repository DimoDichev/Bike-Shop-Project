package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.dto.ModelAddBindingModel;
import bg.softuni.bikeshopapp.model.entity.ManufacturerEntity;
import bg.softuni.bikeshopapp.model.entity.ModelEntity;
import bg.softuni.bikeshopapp.repository.ManufacturerRepository;
import bg.softuni.bikeshopapp.repository.ModelRepository;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import bg.softuni.bikeshopapp.service.ModelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModelServiceTest {

    @Autowired
    private ModelService modelServiceToTest;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ModelRepository modelRepository;

    @BeforeEach
    void setUp() {
        modelRepository.deleteAll();
        manufacturerRepository.deleteAll();
        manufacturerService.save("TestManufacturer");
    }

    @AfterEach
    void tearDown() {
        modelRepository.deleteAll();
        manufacturerRepository.deleteAll();
    }

    @Test
    void testFindIfModelExist() {
        ManufacturerEntity manufacturer = manufacturerRepository.findByName("TestManufacturer").orElse(null);
        ModelEntity model = new ModelEntity().setName("Model").setManufacturer(manufacturer);
        manufacturer.setModels(Set.of(model));
        modelRepository.save(model);

        assertTrue(modelServiceToTest.findIfModelExist("Model"));
        assertFalse(modelServiceToTest.findIfModelExist("NotExist"));
    }

    @Test
    void testSave() {
        ManufacturerEntity manufacturer = manufacturerRepository.findByName("TestManufacturer").orElse(null);
        ModelAddBindingModel model = new ModelAddBindingModel().setName("Model").setManufacturerId(manufacturer.getId());
        modelServiceToTest.save(model);

        Optional<ModelEntity> testModel = modelRepository.findByName("Model");

        assertTrue(testModel.isPresent());
        assertEquals("Model", testModel.get().getName());
        assertEquals(manufacturer.getName(), testModel.get().getManufacturer().getName());
    }

}