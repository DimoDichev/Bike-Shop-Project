package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.binding.ContactUsBindingModel;
import bg.softuni.bikeshopapp.model.entity.ManufacturerEntity;
import bg.softuni.bikeshopapp.model.entity.ModelEntity;
import bg.softuni.bikeshopapp.repository.ContactUsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    void testIndex() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testAbout() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/about")
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testContactUs() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/contact")
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testForgotPassword() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/forgotPassword")
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testContactUsPost() throws Exception {
        assertEquals(0, contactUsRepository.findAll().size());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/contact")
                                .param("email", "test@example.com")
                                .param("message", "Test message")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        assertEquals(1, contactUsRepository.findAll().size());
    }

    @Test
    void testContactUsPostError() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/contact")
                                .param("email", "null")
                                .param("message", "")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:contact"));

        assertEquals(0, contactUsRepository.findAll().size());
    }

}