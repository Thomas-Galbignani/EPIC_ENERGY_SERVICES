package energyservices.EPIC_ENERGY_SERVICES.repositories;

import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatoFatturaRepo extends JpaRepository<StatoFattura, Long> {
    List<StatoFattura> findByFattura(Fattura fattura);

    List<StatoFattura> findByStato(Stato stato);
}
