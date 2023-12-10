package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.entity.ManufacturerEntity;
import bg.softuni.bikeshopapp.repository.ManufacturerRepository;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
class ManufacturerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new ManufacturerController(manufacturerService)).build();
    }

    @AfterEach
    void tearDown() {
        manufacturerRepository.deleteAll();
    }

    @Test
    void testGetProfilesDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers/add"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("manufacturer-add"));
    }

    @Test
    void testAddManufacturer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/manufacturers/add")
                        .param("name", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/panel"));
    }

    @Test
    void testAddManufacturerThrow() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/manufacturers/add")
                        .param("name", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("manufacturerAddBindingModel"))
                .andExpect(redirectedUrl("/manufacturers/add"));
    }

    @Test
    void testAddManufacturerThrowIfExist() throws Exception {
        manufacturerRepository.save(new ManufacturerEntity().setName("Test"));

        mockMvc.perform(MockMvcRequestBuilders.post("/manufacturers/add")
                        .param("name", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("alreadyExist", true))
                .andExpect(redirectedUrl("/manufacturers/add"));
    }


}