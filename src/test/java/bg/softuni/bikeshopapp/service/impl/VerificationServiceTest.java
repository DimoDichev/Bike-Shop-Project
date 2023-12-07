package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.entity.VerificationEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import bg.softuni.bikeshopapp.repository.VerificationRepository;
import bg.softuni.bikeshopapp.service.UserService;
import bg.softuni.bikeshopapp.service.VerificationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VerificationServiceTest {

    @Autowired
    private VerificationService verificationServiceToTest;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        verificationRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        verificationRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void testFindByToken() {
        initUserAndToken();

        VerificationEntity token = verificationServiceToTest.findByToken("6a63543a-24ba-4cbb-8c14-72be1d716584");
        assertNotNull(token);
        assertNull(verificationServiceToTest.findByToken(UUID.randomUUID().toString()));

    }

    @Test
    void testCleanup() {
        initUserAndToken();
        assertEquals(1, verificationRepository.findAll().size());

        verificationServiceToTest.cleanUp();
        assertEquals(0, verificationRepository.findAll().size());
    }

    @Test
    void testReSendVerifyToken() {
        initUserAndToken();
        verificationServiceToTest.cleanUp();
        MockHttpServletRequest request = new MockHttpServletRequest();
        verificationServiceToTest.reSendVerifyEmail("test@example.com", request);
        assertEquals(1, verificationRepository.findAll().size());
    }

    private void initUserAndToken() {
        String token = "6a63543a-24ba-4cbb-8c14-72be1d716584";
        userRoleRepository.save(new UserRoleEntity().setRole(UserRoleEnum.USER));
        UserRoleEntity role = userRoleRepository.findByRole(UserRoleEnum.USER);
        UserEntity user = userRepository.save(new UserEntity()
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setEmail("test@example.com")
                .setPassword(passwordEncoder.encode("00000000"))
                .setEnabled(false)
                .setRoles(Set.of(role)));

        userService.saveToken(user, token);
    }


}