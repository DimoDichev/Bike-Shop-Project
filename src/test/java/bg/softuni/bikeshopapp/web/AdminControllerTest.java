package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.repository.ContactUsRepository;
import bg.softuni.bikeshopapp.service.ContactUsService;
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

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactUsService contactUsService;

    @Autowired
    private ContactUsRepository contactUsRepository;

    @BeforeEach
    void setUp() {
        contactUsRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        contactUsRepository.deleteAll();
    }

    @Test
    void testPanelGet() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/panel")
        ).andExpect(status().is3xxRedirection());


    }

}