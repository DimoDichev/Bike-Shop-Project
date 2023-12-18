package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.AppUserDetails;
import bg.softuni.bikeshopapp.model.dto.UserEditNamesBindingModel;
import bg.softuni.bikeshopapp.model.dto.UserEditPasswordBindingModel;
import bg.softuni.bikeshopapp.model.dto.UserRegistrationDto;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.entity.VerificationEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.model.event.UserRegistrationEvent;
import bg.softuni.bikeshopapp.model.view.UserBaseViewModel;
import bg.softuni.bikeshopapp.model.view.UserViewModel;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import bg.softuni.bikeshopapp.repository.VerificationRepository;
import bg.softuni.bikeshopapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.bikeshopapp.exception.ErrorMessages.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final VerificationRepository verificationRepository;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            VerificationRepository verificationRepository, ModelMapper modelMapper,
            ApplicationEventPublisher applicationEventPublisher, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.verificationRepository = verificationRepository;
        this.modelMapper = modelMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void register(UserRegistrationDto userRegistrationDto, HttpServletRequest request) {
        UserEntity user = userRepository
                .save(modelMapper.map(userRegistrationDto, UserEntity.class));

        applicationEventPublisher
                .publishEvent(new UserRegistrationEvent(user,appUrl(request)));
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        UserEntity user = getUserForEdit(id);

        if (user == null) {
            return false;
        }

        verificationRepository.deleteByUser(user);
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public void activateUser(Long id) {
        UserEntity user = userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(OBJECT_NOT_FOUND, id)));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public boolean deactivateUser(Long id) {
        UserEntity user = getUserForEdit(id);

        if (user == null) {
            return false;
        }

        user.setEnabled(false);
        userRepository.save(user);
        return true;
    }

    @Override
    public void editNames(UserEditNamesBindingModel userEditNamesBindingModel) {
        Long id = userEditNamesBindingModel.getId();

        UserEntity user = userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with id:" + id + " not found"));

        user.setFirstName(userEditNamesBindingModel.getFirstName());
        user.setLastName(userEditNamesBindingModel.getLastName());
        userRepository.save(user);
    }

    @Override
    public boolean changeRole(Long id, String newRole) {
        UserEntity user = getUserForEdit(id);

        if (user == null) {
            return false;
        }

        UserRoleEntity roleAdmin = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        UserRoleEntity roleModerator = userRoleRepository.findByRole(UserRoleEnum.MODERATOR);
        UserRoleEntity roleUser = userRoleRepository.findByRole(UserRoleEnum.USER);

        if (newRole.equals(UserRoleEnum.USER.name())) {
            user.setRoles(Set.of(roleUser));
        } else if (newRole.equals(UserRoleEnum.MODERATOR.name())) {
            user.setRoles(Set.of(roleUser, roleModerator));
        } else if (newRole.equals(UserRoleEnum.ADMIN.name())) {
            user.setRoles(Set.of(roleUser, roleModerator, roleAdmin));
        }

        userRepository.save(user);
        return true;
    }

    @Override
    public void changePassword(UserEditPasswordBindingModel user) {
        UserEntity userToEdit = userRepository
                .findById(user.getId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format(OBJECT_NOT_FOUND, user.getId())));

        userToEdit.setPassword(passwordEncoder.encode(user.getNewPassword()));
        userRepository.save(userToEdit);
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
                        .setEnabled(user.getEnabled())
                        .setUserRole(getRoleAsString(user.getRoles())))
                .orElseThrow (() -> new ObjectNotFoundException("User with id: " + id + " not found"));
    }

    @Override
    public List<UserBaseViewModel> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map( e -> modelMapper.map(e, UserBaseViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkPromiseToEdit(AppUserDetails loggedUser, Long editUserId) {
        if (loggedUser.getId().equals(editUserId)) {
            return true;
        }

        return loggedUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public void saveToken(UserEntity user, String token) {
        VerificationEntity verificationToken = new VerificationEntity(token, user);
        verificationRepository.save(verificationToken);
    }

    @Override
    public void validateToken(String token) {
        VerificationEntity verifyToken = verificationRepository.findByToken(token);

        if (verifyToken != null) {
            UserEntity user = verifyToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    @Override
    public boolean checkActivationStatus(String email) {
        UserEntity user = userRepository
                .findByEmail(email)
                .orElse(null);

        return user != null && user.getEnabled();
    }

    private UserEntity getUserForEdit(Long id) {
        UserEntity user = userRepository
                .findById(id)
                .orElse(null);

        if (user == null) {
            return null;
        }

        boolean isUserAdmin = user.getRoles().stream().anyMatch(r -> r.getRole().equals(UserRoleEnum.ADMIN));
        boolean isOnlyOneAdmin = userRepository.findAllAdminCount() <= 1;

        if (isUserAdmin && isOnlyOneAdmin) {
            return null;
        }

        return user;
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

    private String appUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath();
    }
}
