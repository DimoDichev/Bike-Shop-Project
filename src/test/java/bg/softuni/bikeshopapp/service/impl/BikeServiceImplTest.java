package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.binding.BikeAddBindingModel;
import bg.softuni.bikeshopapp.model.binding.ModelAddBindingModel;
import bg.softuni.bikeshopapp.model.entity.BikeEntity;
import bg.softuni.bikeshopapp.model.entity.ManufacturerEntity;
import bg.softuni.bikeshopapp.model.entity.ModelEntity;
import bg.softuni.bikeshopapp.model.entity.PictureEntity;
import bg.softuni.bikeshopapp.model.enums.CategoryEnum;
import bg.softuni.bikeshopapp.model.enums.FrameMaterialEnum;
import bg.softuni.bikeshopapp.model.enums.FrameSizeEnum;
import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.model.view.BikeDetailsViewModel;
import bg.softuni.bikeshopapp.repository.BikeRepository;
import bg.softuni.bikeshopapp.repository.ManufacturerRepository;
import bg.softuni.bikeshopapp.repository.ModelRepository;
import bg.softuni.bikeshopapp.service.BikeService;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import bg.softuni.bikeshopapp.service.ModelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BikeServiceImplTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelService modelService;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private BikeService bikeServiceToTest;

    @BeforeEach
    void setUp() {
        bikeRepository.deleteAll();
        modelRepository.deleteAll();
        manufacturerRepository.deleteAll();

        manufacturerService.save("Manufacturer");
        ManufacturerEntity manufacturer = manufacturerRepository.findByName("Manufacturer").orElse(null);
        modelRepository.save(new ModelEntity().setName("Model").setManufacturer(manufacturer));
    }

    @AfterEach
    void tearDown() {
        bikeRepository.deleteAll();
        modelRepository.deleteAll();
        manufacturerRepository.deleteAll();
    }

    @Test
    void testSave() {
        ModelEntity model = modelRepository.findByName("Model").orElse(null);
        bikeServiceToTest.save(new BikeAddBindingModel()
                .setModelId(model.getId())
                .setDescription("TestDesc")
                .setPrice(1000.00)
                .setCategory(CategoryEnum.MTB)
                .setFrameSize(FrameSizeEnum.M)
                .setFrameMaterial(FrameMaterialEnum.STEEL)
                .setWheelSize("29"));

        Optional<BikeEntity> bike = bikeRepository.findById(1L);

        assertTrue(bike.isPresent());
        assertEquals(1, bikeRepository.findAll().size());
        assertEquals(1000.00, bike.get().getPrice());
        assertEquals(CategoryEnum.MTB, bike.get().getCategory());
        assertEquals(FrameSizeEnum.M, bike.get().getFrameSize());
        assertEquals(FrameMaterialEnum.STEEL, bike.get().getFrameMaterial());
        assertEquals("29", bike.get().getWheelSize());
        assertEquals(model.getName(), bike.get().getModel().getName());
    }

    @Test
    void testGetAllBikes() {
        initData();
        List<BikeBaseViewModel> allBikes = bikeServiceToTest.getAllBikes();

        BikeBaseViewModel bike = allBikes.get(0);

        assertEquals(10, allBikes.size());
        assertEquals("Manufacturer", bike.getManufacturer());
        assertEquals("Model", bike.getModel());
        assertEquals("/images/bike-icon.jpg", bike.getImageUrl());

    }

    @Test
    void testFindByCategories() {
        initData();
        List<BikeBaseViewModel> bikesMTB = bikeServiceToTest.findByCategories("MTB");
        List<BikeBaseViewModel> bikesROAD = bikeServiceToTest.findByCategories("ROAD");

        assertEquals(10, bikesMTB.size());
        assertEquals(0, bikesROAD.size());
    }

    @Test
    void testGetBikeDetailsById() {
        initData();
        BikeEntity bike = bikeRepository.findAll().get(0);
        BikeDetailsViewModel bikeDetailsById = bikeServiceToTest.getBikeDetailsById(bike.getId());

        assertEquals("Manufacturer", bikeDetailsById.getManufacturer());
        assertEquals("Model", bikeDetailsById.getModel());
        assertEquals("TestDescription", bikeDetailsById.getDescription());
        assertEquals("650B", bikeDetailsById.getWheelSize());
        assertEquals("STEEL", bikeDetailsById.getFrameMaterial());
        assertEquals("M", bikeDetailsById.getSize());
        assertEquals(2000.00, bikeDetailsById.getPrice());
        assertEquals(0, bikeDetailsById.getImagesUrl().size());
    }

    @Test
    void testGetBikeDetailsByIdThrow() {
        assertThrows(ObjectNotFoundException.class, () -> bikeServiceToTest.getBikeDetailsById(100L));
    }

    private void initData() {
        ModelEntity model = modelRepository.findByName("Model").orElse(null);
        for (int i = 0; i <10; i++) {
            bikeRepository.save(new BikeEntity()
                    .setModel(model)
                    .setDescription("TestDescription")
                    .setCategory(CategoryEnum.MTB)
                    .setPrice(2000.00)
                    .setFrameMaterial(FrameMaterialEnum.STEEL)
                    .setWheelSize("650B")
                    .setFrameSize(FrameSizeEnum.M));
        }
    }

}