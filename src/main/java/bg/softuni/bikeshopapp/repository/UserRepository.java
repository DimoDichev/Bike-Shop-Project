package bg.softuni.bikeshopapp.repository;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM users " +
            "join users_roles on users.id = users_roles.user_entity_id " +
            "join bike_shop.roles r on users_roles.roles_id = r.id " +
            "where role like 'ADMIN'")
    Integer findAllAdminCount();
}
