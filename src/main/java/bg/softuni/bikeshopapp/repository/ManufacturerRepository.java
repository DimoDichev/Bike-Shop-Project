package bg.softuni.bikeshopapp.repository;

import bg.softuni.bikeshopapp.model.entity.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {

    Optional<ManufacturerEntity> findByName(String name);

}
