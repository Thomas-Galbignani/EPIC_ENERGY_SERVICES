package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Ruoli;
import energyservices.EPIC_ENERGY_SERVICES.entities.Utente;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.BadRequestException;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.NotFoundException;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.RegisterUtentePayload;
import energyservices.EPIC_ENERGY_SERVICES.repositories.RuoliRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UtenteRepo utRepo;
    @Autowired
    private RuoliRepo ruoliRepo;
    @Autowired
    private RuoliService ruoliService;


    public Utente findById(UUID id) {
        Optional<Utente> found = utRepo.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new NotFoundException(id);
        }
    }

    public Utente salvaUtente(RegisterUtentePayload payload) {
        if (utRepo.existsByEmail(payload.getEmail())) {
            throw new BadRequestException("l'email " + payload.getEmail() + " è già in uso");
        }
        Utente u = new Utente(payload.getUsername(), payload.getName(), payload.getSurname(), payload.getEmail(), payload.getPassword());
        Ruoli role = null;
        //assegnazione ruolo user di default
        if (!ruoliRepo.existsByNomeRuolo("User")) {
            Ruoli r = new Ruoli("User");
            ruoliRepo.save(r);

        }
        role = ruoliService.findByRuolo("User");
        //--------------------------------------------------------
        //salvo nella tabella intermedia l'utente e il ruolo di default (User)
        u.setRuolo(role);
        utRepo.save(u);

        return u;
    }

    public Utente salvaAdmin(RegisterUtentePayload payload) {
        if (utRepo.existsByEmail(payload.getEmail())) {
            throw new BadRequestException("l'email " + payload.getEmail() + " è già in uso");
        }
        Utente u = new Utente(payload.getUsername(), payload.getName(), payload.getSurname(), payload.getEmail(), payload.getPassword());
        Ruoli role = null;
        //assegnazione ruolo user di default
        if (!ruoliRepo.existsByNomeRuolo("Admin")) {
            Ruoli r = new Ruoli("Admin");
            ruoliRepo.save(r);

        }
        role = ruoliService.findByRuolo("Admin");
        //--------------------------------------------------------
        //salvo nella tabella intermedia l'utente e il ruolo di default (User)
        u.setRuolo(role);
        utRepo.save(u);

        return u;
    }
}
