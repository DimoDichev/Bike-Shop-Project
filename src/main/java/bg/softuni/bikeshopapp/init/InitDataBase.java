package bg.softuni.bikeshopapp.init;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import bg.softuni.bikeshopapp.repository.UserRepository;
import bg.softuni.bikeshopapp.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InitDataBase implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_PASS = System.getenv("ADMIN_APP_PASS");

    public InitDataBase(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() == 0) {
            userRoleRepository.save(new UserRoleEntity(UserRoleEnum.ADMIN));
            userRoleRepository.save(new UserRoleEntity(UserRoleEnum.USER));
            userRepository.save(new UserEntity("admin@admin.com",
                    passwordEncoder.encode(ADMIN_PASS),
                    "Admin",
                    "Adminov",
                    Set.of(userRoleRepository.findByRole(UserRoleEnum.USER),
                            userRoleRepository.findByRole(UserRoleEnum.ADMIN))));
        }
    }
}
