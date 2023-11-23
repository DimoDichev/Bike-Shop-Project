package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.event.UserRegistrationEvent;
import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.model.view.UserViewModel;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import bg.softuni.bikeshopapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void register(UserRegistrationBindingModel userRegistrationBindingModel) {
        userRepository
                .save(modelMapper.map(userRegistrationBindingModel, UserEntity.class));

        applicationEventPublisher
                .publishEvent(new UserRegistrationEvent("UserService", userRegistrationBindingModel.getEmail(), userRegistrationBindingModel.getFullName()));
    }

    @Override
    public void activateUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User with id: " + id + " not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent() && !user.get().getEmail().equals("admin@admin.com")) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public boolean findIfEmailExist(String email) {
        return userRepository.findByEmail(email).orElse(null) != null;
    }

    @Override
    public UserViewModel getUserProfile(Long id) {
        return userRepository
                .findById(id)
                .map(user -> new UserViewModel()
                        .setId(user.getId())
                        .setEmail(user.getEmail())
                        .setFirstName(user.getFirstName())
                        .setLastname(user.getLastName())
                        .setUserRole(getRoleAsString(user.getRoles())))
                .orElseThrow (() -> new ObjectNotFoundException("User with id: " + id + " not found"));
    }

    private String getRoleAsString(Set<UserRoleEntity> rolesEntity) {
        List<String> roles = rolesEntity.stream().map(r -> r.getRole().name()).toList();

        if (roles.contains("ADMIN")) {
            return "ADMIN";
        } else if (roles.contains("MODERATOR")) {
            return "MODERATOR";
        } else {
            return "USER";
        }
    }

    @Override
    public List<UserBaseViewModel> getAllNotActivated() {
        return userRepository
                .findAllByEnabledFalse()
                .stream()
                .map( e -> modelMapper.map(e, UserBaseViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserBaseViewModel> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map( e -> modelMapper.map(e, UserBaseViewModel.class))
                .collect(Collectors.toList());
    }
}
