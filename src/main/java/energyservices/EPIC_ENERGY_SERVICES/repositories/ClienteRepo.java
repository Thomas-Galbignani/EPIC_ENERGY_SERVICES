package energyservices.EPIC_ENERGY_SERVICES.repositories;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, UUID> {


}
