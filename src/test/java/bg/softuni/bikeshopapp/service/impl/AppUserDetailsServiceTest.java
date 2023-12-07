package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {

    private AppUserDetailsService serviceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new AppUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserNotFound() {
        assertThrows(ObjectNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("test@example.com"));
    }

    @Test
    void testUserFound() {
        UserEntity testUser = createTestUser();
        when(mockUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getEmail());

        assertNotNull(userDetails);
        assertEquals(testUser.getEmail(), userDetails.getUsername(), "Username not mapped to email!");
        assertEquals(testUser.getPassword(), userDetails.getPassword(), "Password not mapped correctly!");
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN));
        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER));
    }

    private boolean containsAuthority(UserDetails userDetails, String authority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> authority.equals(a.getAuthority()));
    }

    private static UserEntity createTestUser() {
        return new UserEntity()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("test@example.com")
                .setPassword("test")
                .setEnabled(true)
                .setRoles(Set.of(
                        new UserRoleEntity().setRole(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setRole(UserRoleEnum.USER)
                ));
    }

}