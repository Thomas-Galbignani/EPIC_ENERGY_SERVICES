package energyservices.EPIC_ENERGY_SERVICES.repositories;

import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatoRepo extends JpaRepository<Stato, Long> {
    List<Stato> findByNomeStato(String nomeStato);
}
