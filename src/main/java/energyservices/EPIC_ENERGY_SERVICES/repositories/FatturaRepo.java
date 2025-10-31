package energyservices.EPIC_ENERGY_SERVICES.repositories;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FatturaRepo extends JpaRepository<Fattura, Long>, JpaSpecificationExecutor<Fattura> {
    List<Fattura> findByCliente(Cliente cliente);
}
