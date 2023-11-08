package bg.softuni.bikeshopapp.config;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.entity.BikeEntity;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.model.view.BikeBaseViewModel;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}
