package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Comune;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.NotFoundException;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ComuneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepo comuneRepo;

    public Comune findById(String nomeComune) {
        Optional<Comune> found = comuneRepo.findById(nomeComune);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new NotFoundException(UUID.fromString(nomeComune));
        }
    }
}
