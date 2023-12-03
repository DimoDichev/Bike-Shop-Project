package bg.softuni.bikeshopapp.repository;

import bg.softuni.bikeshopapp.model.entity.ContactUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUsEntity, Long> {

    List<ContactUsEntity> findAllBy();

}
