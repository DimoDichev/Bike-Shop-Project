package bg.softuni.bikeshopapp.web;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
class UserAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new UserAuthenticationController(userService, verificationService)).build();
        userRoleRepository.save(new UserRoleEntity().setRole(UserRoleEnum.USER));
    }

    @AfterEach
    void tearDown() {
        verificationRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void testGetRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("user-register"));
    }

    @Test
    void testGetLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("user-login"));
    }

    @Test
    void testUserRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "email@email.com")
                        .param("password", "00000000")
                        .param("confirmPassword", "00000000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"));
    }

    @Test
    void testUserRegisterBindingError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("email", "")
                        .param("password", "")
                        .param("confirmPassword", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));
    }

    @Test
    void testUserRegisterDifferentPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "email@email.com")
                        .param("password", "00000000")
                        .param("confirmPassword", "11111111"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("passwordNotEquals", true))
                .andExpect(redirectedUrl("register"));
    }

    @Test
    void testUserRegisterDuplicateEmail() throws Exception {
        init();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "test@test.com")
                        .param("password", "00000000")
                        .param("confirmPassword", "00000000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("emailExist", true))
                .andExpect(redirectedUrl("register"));
    }

    @Test
    void testUserRegisterLoginError() throws Exception {
        init();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login-error")
                        .param("email", "test@test.com"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("user-login"));
    }

    @Test
    void testUserRegisterVerifyRedirect() throws Exception {
        init();
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register/verify")
                        .param("token", "11111111"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("user-login"));
    }

    @Test
    void testUserRegisterVerifyRedirectWithDisabledUser() throws Exception {
        initDisabledUser();
        UserEntity user = userRepository.findAll().stream().findFirst().orElse(null);
        verificationRepository.save(new VerificationEntity().setToken("11111111")
                .setUser(user));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register/verify")
                        .param("token", "11111111"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("user-login"));
    }

    @Test
    void testUserActivationById() throws Exception {
        initDisabledUser();
        UserEntity user = userRepository.findAll().stream().findFirst().orElse(null);
        Long id = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/activation/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profiles/" + id));
    }

    private void init() {
        UserRoleEntity userRoleEntity = userRoleRepository.findAll().stream().findFirst().orElse(null);

        userRepository.save(new UserEntity()
                .setEmail("test@test.com")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEnabled(true)
                .setRoles(Set.of(userRoleEntity))
                .setPassword("11111111"));
    }

    private void initDisabledUser() {
        UserRoleEntity userRoleEntity = userRoleRepository.findAll().stream().findFirst().orElse(null);

        userRepository.save(new UserEntity()
                .setEmail("test@test.com")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEnabled(false)
                .setRoles(Set.of(userRoleEntity))
                .setPassword("11111111"));
    }

}