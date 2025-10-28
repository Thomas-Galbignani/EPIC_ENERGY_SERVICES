package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Ruoli;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.NotFoundException;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.RuoloDto;
import energyservices.EPIC_ENERGY_SERVICES.repositories.RuoliRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RuoliService {
    @Autowired
    private RuoliRepo ruoliRepo;

    public Ruoli findByRuolo(String nomeRuolo) {
        Optional found = ruoliRepo.findByNomeRuolo(nomeRuolo);
        if (found.isPresent()) {
            return (Ruoli) found.get();
        } else {
            throw new NotFoundException(UUID.fromString(nomeRuolo));
        }
    }

    public Ruoli saveRuolo(RuoloDto payload) {
        Ruoli r = new Ruoli(payload.nomeRuolo());
        ruoliRepo.save(r);
        return r;
    }
}
