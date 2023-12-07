package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.binding.ContactUsBindingModel;
import bg.softuni.bikeshopapp.model.entity.ContactUsEntity;
import bg.softuni.bikeshopapp.model.view.ContactUsView;
import bg.softuni.bikeshopapp.repository.ContactUsRepository;
import bg.softuni.bikeshopapp.service.ContactUsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactUsServiceImplTest {

    @Autowired
    private ContactUsService contactUsServiceToTest;

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
    void testSend() {
        ContactUsBindingModel msg = new ContactUsBindingModel()
                .setEmail("test@example.com")
                .setMessage("Test msg");

        assertEquals(0, contactUsRepository.findAll().size());
        contactUsServiceToTest.send(msg);
        assertEquals(1, contactUsRepository.findAll().size());
    }

    @Test
    void testGetMessage() {
        initData();
        Long id = contactUsRepository.findAll().get(0).getId();

        ContactUsView message = contactUsServiceToTest.getMessage(id);

        assertEquals("Msg", message.getMessage());
        assertEquals("Test", message.getEmail());
    }

    @Test
    void testGetMessageThrow() {
        initData();
        assertThrows(ObjectNotFoundException.class, () -> contactUsServiceToTest.getMessage(1000L));
    }

    @Test
    void testAllMsg() {
        initData();
        List<ContactUsView> contactUsViews = contactUsServiceToTest.allMsg();
        assertEquals(10, contactUsViews.size());
    }

    @Test
    void testCount() {
        initData();
        assertEquals(10, contactUsServiceToTest.countMsg());
        assertNotEquals(11, contactUsServiceToTest.countMsg());
    }

    @Test
    void testDelete() {
        initData();
        Long id = contactUsRepository.findAll().get(0).getId();

        assertEquals(10, contactUsServiceToTest.countMsg());
        contactUsServiceToTest.delete(id);
        assertEquals(9, contactUsServiceToTest.countMsg());
    }

    private void initData() {
        for (int i = 1; i <= 10; i++) {
            ContactUsEntity msg = new ContactUsEntity()
                    .setEmail("Test")
                    .setMessage("Msg");

            contactUsRepository.save(msg);
        }
    }

}