package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.entity.ContactUsEntity;
import bg.softuni.bikeshopapp.repository.ContactUsRepository;
import bg.softuni.bikeshopapp.service.ContactUsService;
import bg.softuni.bikeshopapp.service.EmailService;
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
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactUsService contactUsService;

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        contactUsRepository.deleteAll();
        mockMvc = standaloneSetup(new AdminController(contactUsService, emailService)).build();
        contactUsRepository.save(new ContactUsEntity().setEmail("test@example.cpm").setMessage("test"));
    }

    @AfterEach
    void tearDown() {
        contactUsRepository.deleteAll();
    }

    @Test
    void testPanelGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/panel"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("admin-panel"))
                .andExpect(model().attributeExists("msgCount"));
    }

    @Test
    void testMessagesById() throws Exception {
        ContactUsEntity msg = contactUsRepository.findAll().stream().findFirst().orElse(null);
        Long id = msg.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/messages/" + id))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("message-details"));
    }

    @Test
    void testMessagesAnswer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/messages/answer/1").param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/messages"));
    }

    @Test
    void testMessagesDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/messages/delete/1").param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/messages"));
    }

}