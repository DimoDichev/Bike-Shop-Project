package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.binding.UserRegistrationBindingModel;
import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.view.UserNotActivatedViewModel;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void register(UserRegistrationBindingModel userRegistrationBindingModel) {
        userRepository.save(modelMapper.map(userRegistrationBindingModel, UserEntity.class));
    }

    @Override
    public boolean findIfEmailExist(String email) {
        return userRepository.findByEmail(email).orElse(null) != null;
    }

    @Override
    public List<UserNotActivatedViewModel> getAllNotActivated() {
        return userRepository
                .findAllByEnabledFalse()
                .stream()
                .map(e -> new UserNotActivatedViewModel()
                        .setId(e.getId())
                        .setEmail(e.getEmail())
                        .setFirstName(e.getFirstName())
                        .setLastName(e.getLastName()))
                .collect(Collectors.toList());
    }
}
