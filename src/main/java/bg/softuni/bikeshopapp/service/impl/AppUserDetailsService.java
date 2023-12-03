package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.AppUserDetails;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository
                .findByEmail(email)
                .map(AppUserDetailsService::map)
                .orElseThrow(() -> new ObjectNotFoundException(email + " does not exist!"));
    }

    private static AppUserDetails map(UserEntity userEntity) {
        return new AppUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEnabled(),
                userEntity.getRoles()
                        .stream()
                        .map(AppUserDetailsService::mapAuthorities)
                        .toList()
        );
    }

    private static GrantedAuthority mapAuthorities(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRoleEntity.getRole().name()
        );
    }
}
