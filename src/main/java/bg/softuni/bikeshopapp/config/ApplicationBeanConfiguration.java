package bg.softuni.bikeshopapp.config;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import bg.softuni.bikeshopapp.service.impl.AppUserDetailsService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class ApplicationBeanConfiguration {

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationBeanConfiguration(UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // UserRegistrationBindingModel -> UserEntity
        Provider<UserEntity> newUserProvider = req -> new UserEntity()
                .setEnabled(false)
                .setRoles(Set.of(userRoleRepository.findByRole(UserRoleEnum.USER)));


        Converter<String, String> passwordConverter
                = ctx -> (ctx.getSource() == null)
                ? null
                : passwordEncoder.encode(ctx.getSource());

        modelMapper
                .createTypeMap(UserRegistrationBindingModel.class, UserEntity.class)
                .setProvider(newUserProvider)
                .addMappings(mapper -> mapper.using(passwordConverter)
                        .map(UserRegistrationBindingModel::getPassword, UserEntity::setPassword));

        return modelMapper;
    }

    @Bean
    public UserDetailsService appUserDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }
}
