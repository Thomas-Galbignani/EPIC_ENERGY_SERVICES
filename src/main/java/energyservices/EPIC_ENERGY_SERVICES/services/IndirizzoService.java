package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Comune;
import energyservices.EPIC_ENERGY_SERVICES.entities.Indirizzo;
import energyservices.EPIC_ENERGY_SERVICES.enums.TipoSede;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.BadRequestException;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.NotFoundException;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewIndirizzoPayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.IndirizzoResDTO;
import energyservices.EPIC_ENERGY_SERVICES.repositories.IndirizzoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepo indirizzoRepo;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ComuneService comuneService;

    public Indirizzo findById(UUID id) {
        Optional<Indirizzo> found = indirizzoRepo.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new NotFoundException(id);
        }
    }

    public IndirizzoResDTO SalvaIndirizzo(NewIndirizzoPayload payload, UUID idCliente) {
        Cliente cliente = clienteService.findById(idCliente);
        List<Indirizzo> indirizzi = indirizzoRepo.findByCliente(cliente);
        if (indirizzi.size() >= 2) {
            throw new BadRequestException("questo cliente ha già 2 indirizzi");
        }
        Comune comune = comuneService.findById(payload.getNomeComune());
        Indirizzo ind = new Indirizzo(payload.getVia(), payload.getCivico(), payload.getCap(), TipoSede.SEDE_OPERATIVA, comune, cliente);
        Indirizzo saved = indirizzoRepo.save(ind);
        IndirizzoResDTO res = new IndirizzoResDTO(saved.getUuid().toString(), saved.getVia(), saved.getCivico(), saved.getCap(), comune.getDenominazione(), idCliente.toString());
        return res;
    }

    public void cancellaIndirizzo(UUID id) {
        Indirizzo found = this.findById(id);
        indirizzoRepo.delete(found);
    }

}
