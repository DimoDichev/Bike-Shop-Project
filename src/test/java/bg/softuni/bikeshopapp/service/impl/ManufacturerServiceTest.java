package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.entity.ManufacturerEntity;
import bg.softuni.bikeshopapp.model.entity.ModelEntity;
import bg.softuni.bikeshopapp.model.view.ManufacturerNameViewModel;
import bg.softuni.bikeshopapp.model.view.ManufacturerViewModel;
import bg.softuni.bikeshopapp.model.view.ModelViewModel;
import bg.softuni.bikeshopapp.repository.ManufacturerRepository;
import bg.softuni.bikeshopapp.repository.ModelRepository;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManufacturerServiceTest {

    @Autowired
    private ManufacturerService manufacturerServiceToTest;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ModelRepository modelRepository;

    @BeforeEach
    void setUp() {
        modelRepository.deleteAll();
        manufacturerRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        modelRepository.deleteAll();
        manufacturerRepository.deleteAll();
    }

    @Test
    void testSavaManufacturer() {
        manufacturerServiceToTest.save("TestSaveManufacturer");
        assertEquals(1, manufacturerRepository.findAll().size());
        assertEquals("TestSaveManufacturer", manufacturerRepository.findByName("TestSaveManufacturer").get().getName());
    }

    @Test
    void testFindIfManufacturerExist() {
        manufacturerServiceToTest.save("TestSaveManufacturer");
        assertTrue(manufacturerServiceToTest.findIfManufacturerExist("TestSaveManufacturer"));
        assertFalse(manufacturerServiceToTest.findIfManufacturerExist("NotExisting"));

    }

    @Test
    void testGetAllManufacturersNames() {
        manufacturerServiceToTest.save("TestSaveManufacturer1");
        manufacturerServiceToTest.save("TestSaveManufacturer2");
        manufacturerServiceToTest.save("TestSaveManufacturer3");

        List<ManufacturerNameViewModel> manufacturers = manufacturerServiceToTest.getAllManufacturerNames();
        List<String> names = manufacturers.stream().map(ManufacturerNameViewModel::getName).toList();

        assertEquals(3, manufacturers.size());
        assertTrue(names.contains("TestSaveManufacturer1"));
        assertTrue(names.contains("TestSaveManufacturer2"));
        assertTrue(names.contains("TestSaveManufacturer3"));
    }

    @Test
    void testGetAllManufacturersWithModels() {
        manufacturerServiceToTest.save("TestSaveManufacturer1");
        manufacturerServiceToTest.save("TestSaveManufacturer2");

        ManufacturerEntity manufacturer1 = manufacturerRepository.findByName("TestSaveManufacturer1").orElse(null);

        ModelEntity model1 = new ModelEntity().setName("Model1").setManufacturer(manufacturer1);
        ModelEntity model2 = new ModelEntity().setName("Model2").setManufacturer(manufacturer1);
        ModelEntity model3 = new ModelEntity().setName("Model3").setManufacturer(manufacturer1);
        modelRepository.save(model1);
        modelRepository.save(model2);
        modelRepository.save(model3);

        List<ManufacturerViewModel> manufacturers = manufacturerServiceToTest.getAllManufacturersWithModels();
        List<String> result = new ArrayList<>();

        for (ManufacturerViewModel manufacturer : manufacturers) {
            result.addAll(manufacturer.getModels().stream().map(ModelViewModel::getName).toList());
        }

        assertEquals(2, manufacturers.size());
        assertEquals(3, result.size());
    }





}