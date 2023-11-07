package bg.softuni.bikeshopapp.repository;

import bg.softuni.bikeshopapp.model.entity.UserRoleEntity;
import bg.softuni.bikeshopapp.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByRole(UserRoleEnum role);

}
