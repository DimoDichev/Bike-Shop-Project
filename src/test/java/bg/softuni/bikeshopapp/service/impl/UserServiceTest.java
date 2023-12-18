package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.AppUserDetails;
import bg.softuni.bikeshopapp.model.dto.UserEditNamesBindingModel;
import bg.softuni.bikeshopapp.model.dto.UserEditPasswordBindingModel;
import bg.softuni.bikeshopapp.model.dto.UserRegistrationDto;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.model.view.UserViewModel;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import bg.softuni.bikeshopapp.repository.VerificationRepository;
import bg.softuni.bikeshopapp.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userServiceToTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @BeforeEach
    void setUp() {
        verificationRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();

        userRoleRepository.save(new UserRoleEntity().setRole(UserRoleEnum.USER));
        userRoleRepository.save(new UserRoleEntity().setRole(UserRoleEnum.MODERATOR));
        userRoleRepository.save(new UserRoleEntity().setRole(UserRoleEnum.ADMIN));
    }

    @AfterEach
    void tearDown() {
        verificationRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void testRegisterUser() {
        UserRegistrationDto newUser = new UserRegistrationDto()
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setEmail("test@example.com")
                .setPassword("00000000")
                .setConfirmPassword("00000000");

        MockHttpServletRequest request = new MockHttpServletRequest();

        assertEquals(0, userRepository.findAll().size());
        userServiceToTest.register(newUser, request);
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void testDeleteUser() {
        registerUser();
        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);

        assertEquals(1, userRepository.findAll().size());
        assertTrue(userServiceToTest.deleteUser(user.getId()));
        assertFalse(userServiceToTest.deleteUser(100000L));
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    void testActivateUser() {
        registerUser();
        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);

        assertFalse(user.getEnabled());
        userServiceToTest.activateUser(user.getId());

        UserEntity testUser = userRepository.findByEmail("test@example.com").orElse(null);
        assertTrue(testUser.getEnabled());
    }

    @Test
    void testActivateUserThrow() {
        assertThrows(ObjectNotFoundException.class, () -> userServiceToTest.activateUser(10000L));
    }

    @Test
    void testDeactivateUser() {
        registerUser();
        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);
        user.setEnabled(true);
        userRepository.save(user);

        assertTrue(userServiceToTest.deactivateUser(user.getId()));
        user = userRepository.findByEmail("test@example.com").orElse(null);
        assertFalse(user.getEnabled());
        assertFalse(userServiceToTest.deactivateUser(10000L));
    }

    @Test
    void testEditNames() {
        registerUser();
        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);

        UserEditNamesBindingModel userToEdit = new UserEditNamesBindingModel()
                .setFirstName("EditFirst")
                .setLastName("EditLast")
                .setId(user.getId());

        userServiceToTest.editNames(userToEdit);

        user = userRepository.findByEmail("test@example.com").orElse(null);

        assertEquals("EditFirst", user.getFirstName());
        assertEquals("EditLast", user.getLastName());
    }

    @Test
    void testEditNamesThrow() {
        UserEditNamesBindingModel userToEdit = new UserEditNamesBindingModel()
                .setFirstName("EditFirst")
                .setLastName("EditLast")
                .setId(10000L);
        assertThrows(ObjectNotFoundException.class, () -> userServiceToTest.editNames(userToEdit));
    }

    @Test
    void testChangeRole() {
        registerUser();
        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);

        userServiceToTest.changeRole(user.getId(), "USER");
        user = userRepository.findByEmail("test@example.com").orElse(null);
        assertEquals("USER",
                user.getRoles().stream().filter(r -> r.getRole().name().equals("USER")).findFirst().get().getRole().name());

        userServiceToTest.changeRole(user.getId(), "MODERATOR");
        user = userRepository.findByEmail("test@example.com").orElse(null);
        assertEquals("MODERATOR",
                user.getRoles().stream().filter(r -> r.getRole().name().equals("MODERATOR")).findFirst().get().getRole().name());

        userServiceToTest.changeRole(user.getId(), "ADMIN");
        user = userRepository.findByEmail("test@example.com").orElse(null);
        assertEquals("ADMIN",
                user.getRoles().stream().filter(r -> r.getRole().name().equals("ADMIN")).findFirst().get().getRole().name());
    }

    @Test
    void testChangeRoleFalse() {
        assertFalse(userServiceToTest.changeRole(10000L, "USER"));
    }

    @Test
    void testChangePassword() {
        registerUser();
        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);

        UserEditPasswordBindingModel editUser = new UserEditPasswordBindingModel()
                .setId(user.getId())
                .setNewPassword("11111111")
                .setConfirmNewPassword("11111111");

        userServiceToTest.changePassword(editUser);

        user = userRepository.findByEmail("test@example.com").orElse(null);

        assertTrue(passwordEncoder.matches("11111111", user.getPassword()));
    }

    @Test
    void testChangePasswordThrow() {
        UserEditPasswordBindingModel editUser = new UserEditPasswordBindingModel()
                .setId(10000L)
                .setNewPassword("11111111")
                .setConfirmNewPassword("11111111");

        assertThrows(ObjectNotFoundException.class, () -> userServiceToTest.changePassword(editUser));
    }

    @Test
    void testFindEmailExist() {
        registerUser();
        assertTrue(userServiceToTest.findIfEmailExist("test@example.com"));
        assertFalse(userServiceToTest.findIfEmailExist("notExist@example.com"));
    }

    @Test
    void testGetUserProfile() {
        registerUser();
        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);

        UserViewModel userProfile = userServiceToTest.getUserProfile(user.getId());

        assertEquals("FirstName", userProfile.getFirstName());
        assertEquals("LastName", userProfile.getLastname());
        assertEquals("test@example.com", userProfile.getEmail());
        assertFalse(userProfile.getEnabled());
        assertEquals("USER", userProfile.getUserRole());
    }

    @Test
    void testGetUserProfileThrow() {
        assertThrows(ObjectNotFoundException.class, () -> userServiceToTest.getUserProfile(10000L));
    }

    @Test
    void testGetAllUser() {
        assertEquals(0, userServiceToTest.getAllUsers().size());
        registerUser();
        List<UserBaseViewModel> allUsers = userServiceToTest.getAllUsers();
        assertEquals(1, allUsers.size());

        UserBaseViewModel userBaseViewModel = allUsers.get(0);

        assertEquals("FirstName", userBaseViewModel.getFirstName());
        assertEquals("LastName", userBaseViewModel.getLastName());
        assertEquals("test@example.com", userBaseViewModel.getEmail());

        registerSecondUser();
        assertEquals(2, userServiceToTest.getAllUsers().size());
    }

    @Test
    void testCheckPromise() {
        registerUser();
        registerSecondUser();

        UserEntity user = userRepository.findByEmail("test@example.com").orElse(null);
        UserEntity user2 = userRepository.findByEmail("Secondtest@example.com").orElse(null);

        AppUserDetails appUserDetails = new AppUserDetails(
                1000L,
                "app@example.com",
                passwordEncoder.encode("00000000"),
                "appFirst",
                "appLast",
                true,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        AppUserDetails secondAppUserDetails = new AppUserDetails(
                user2.getId(),
                "appsec@example.com",
                passwordEncoder.encode("00000000"),
                "secappFirst",
                "secappLast",
                true,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        assertTrue(userServiceToTest.checkPromiseToEdit(appUserDetails, user.getId()));
        assertFalse(userServiceToTest.checkPromiseToEdit(secondAppUserDetails, user.getId()));
        assertTrue(userServiceToTest.checkPromiseToEdit(secondAppUserDetails, user2.getId()));
    }

    @Test
    void testCheckActivationStatus() {
        registerUser();
        registerSecondUser();

        assertTrue(userServiceToTest.checkActivationStatus("Secondtest@example.com"));
        assertFalse(userServiceToTest.checkActivationStatus("test@example.com"));
        assertFalse(userServiceToTest.checkActivationStatus("notExist@example.com"));
    }

    private void registerUser() {
        UserRoleEntity role = userRoleRepository.findByRole(UserRoleEnum.USER);

        userRepository.save(new UserEntity()
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setEmail("test@example.com")
                .setPassword(passwordEncoder.encode("00000000"))
                .setEnabled(false)
                .setRoles(Set.of(role)));
    }

    private void registerSecondUser() {
        UserRoleEntity role = userRoleRepository.findByRole(UserRoleEnum.MODERATOR);

        userRepository.save(new UserEntity()
                .setFirstName("SecondFirstName")
                .setLastName("SecondLastName")
                .setEmail("Secondtest@example.com")
                .setPassword(passwordEncoder.encode("00000000"))
                .setEnabled(true)
                .setRoles(Set.of(role)));
    }


}