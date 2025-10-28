package energyservices.EPIC_ENERGY_SERVICES.repositories;

import energyservices.EPIC_ENERGY_SERVICES.entities.Ruoli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuoliRepo extends JpaRepository<Ruoli, Long> {
    boolean existsByNomeRuolo(String nomeRuolo);

    Optional<Ruoli> findByNomeRuolo(String nomeRuolo);
}
