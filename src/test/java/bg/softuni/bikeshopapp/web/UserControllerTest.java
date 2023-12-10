package bg.softuni.bikeshopapp.web;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import bg.softuni.bikeshopapp.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new UserController(userService)).build();
        userRoleRepository.save(new UserRoleEntity().setRole(UserRoleEnum.USER));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void testGetProfilesDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profiles"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allUsers"))
                .andExpect(forwardedUrl("users-profiles"));
    }

    @Test
    void testProfileEdit() throws Exception {
        init();

        UserEntity user = userRepository.findAll().stream().findFirst().orElse(null);
        Long id = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/profiles/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(forwardedUrl("user-profile-edit"));
    }

    @Test
    void testProfileEditPost() throws Exception {
        init();

        UserEntity user = userRepository.findAll().stream().findFirst().orElse(null);
        Long id = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/profiles/edit/names/" + id)
                        .param("id", String.valueOf(id))
                        .param("firstName", "firstName")
                        .param("lastName", "lastName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profiles/" + id));
    }

    @Test
    void testProfileEditPostThrow() throws Exception {
        init();

        UserEntity user = userRepository.findAll().stream().findFirst().orElse(null);
        Long id = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/profiles/edit/names/" + id)
                        .param("id", String.valueOf(id))
                        .param("firstName", "")
                        .param("lastName", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profiles/edit/" + id));
    }

    @Test
    void testProfileEditRole() throws Exception {
        init();

        UserEntity user = userRepository.findAll().stream().findFirst().orElse(null);
        Long id = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/profiles/edit/role/" + id)
                        .param("id", String.valueOf(id))
                        .param("userRole", "ROLE_USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profiles/" + id));
    }

    @Test
    void testProfileEditRoleThrow() throws Exception {
        init();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/profiles/edit/role/20000")
                        .param("id", String.valueOf(20000))
                        .param("userRole", "ROLE_USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errorChangeRole", true))
                .andExpect(redirectedUrl("/users/profiles/edit/20000"));
    }

    @Test
    void testProfileDelete() throws Exception {
        init();

        UserEntity user = userRepository.findAll().stream().findFirst().orElse(null);
        Long id = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/profiles/edit/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profiles"));
    }

    @Test
    void testProfileDeleteNotSuccessful() throws Exception {
        init();;

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/profiles/edit/delete/20000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorDelete"))
                .andExpect(redirectedUrl("/users/profiles/edit/20000"));
    }



//    @Test
//    void testProfileDetails() throws Exception {
//        init();
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/profiles/1")
//                        .param("id", "1")
//                        .param("email", "email@email.com")
//                        .param("password", "11111111")
//                        .param("firstName", "firstName")
//                        .param("lastName", "lastName")
//                        .param("isEnabled", "true")
//                        .param("authorities", "{ROLE_ADMIN}"))
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl("/users/profiles/1"));
//    }

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

}