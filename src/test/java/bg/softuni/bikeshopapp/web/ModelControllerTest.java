package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.service.ManufacturerService;
import bg.softuni.bikeshopapp.service.ModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAddModel() {
    }

    @Test
    void testAddModel() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/models/add")
                        .param("name", "TestModel")
                        .param("manufacturerId", "1")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void modelAddBindingModel() {
    }
}