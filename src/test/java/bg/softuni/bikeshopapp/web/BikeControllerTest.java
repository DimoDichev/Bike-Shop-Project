package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.service.BikeService;
import bg.softuni.bikeshopapp.service.ManufacturerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
class BikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BikeService bikeService;

    @Autowired
    private ManufacturerService manufacturerService;


    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new BikeController(bikeService, manufacturerService)).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testByCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bikes/MTB"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bikesBaseView"))
                .andExpect(forwardedUrl("bikes-view"));
    }

    @Test
    void testByAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bikes/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bikesBaseView"))
                .andExpect(forwardedUrl("bikes-view"));
    }

    @Test
    void testBikeAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bikes/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("manufacturers"))
                .andExpect(forwardedUrl("bike-add"));
    }

}