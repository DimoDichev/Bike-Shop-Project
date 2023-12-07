package bg.softuni.bikeshopapp.repository;

import bg.softuni.bikeshopapp.model.entity.UserEntity;
import bg.softuni.bikeshopapp.model.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {

    VerificationEntity findByToken(String token);
    VerificationEntity findByUser_Email(String email);

    void deleteByUser(UserEntity user);
}
