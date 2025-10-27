package energyservices.EPIC_ENERGY_SERVICES.repositories;

import energyservices.EPIC_ENERGY_SERVICES.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepo extends JpaRepository<Provincia, String> {
}
