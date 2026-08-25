package ua.ellka.booking.resource.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ellka.booking.resource.model.Property;

import java.util.List;

@Repository
public interface PropertyRepo extends JpaRepository<Property, Long> {

    List<Property> findAllByLocationIgnoreCaseAndActive(String location, Boolean active);

    List<Property> findAllByActive(Boolean active);

}
