package ua.ellka.booking.resource.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ellka.booking.resource.model.Property;

@Repository
public interface PropertyRepo extends JpaRepository<Property, Long> {

}
