package bg.softuni.bikeshopapp.repository;

import bg.softuni.bikeshopapp.model.entity.BikeEntity;
import bg.softuni.bikeshopapp.model.enums.ModelCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<BikeEntity, Long> {

    List<BikeEntity> findByModel_CategoryOrderById(ModelCategoryEnum model_category);

}
