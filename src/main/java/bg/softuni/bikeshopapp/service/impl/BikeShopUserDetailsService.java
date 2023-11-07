package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
public class BikeShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BikeShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .map(BikeShopUserDetailsService::map)
                .orElseThrow(ObjectNotFoundException::new);
    }

    private static UserDetails map(UserEntity userEntity) {
        return User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(BikeShopUserDetailsService::mapAuthorities).toList())
                .build();
    }

    private static GrantedAuthority mapAuthorities(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRoleEntity.getRole().name()
        );
    }
}
