package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.NotFoundException;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewFatturaDTO;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.FatturaResponse;
import energyservices.EPIC_ENERGY_SERVICES.repositories.FatturaRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.StatoFatturaRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.StatoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepo fatturaRepo;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private StatoFatturaRepo statoFatturaRepo;
    @Autowired
    private StatoRepo statoRepo;

    public Fattura findById(long id) {
        Optional<Fattura> found = fatturaRepo.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            String idStr = String.valueOf(id);
            throw new NotFoundException(UUID.fromString(idStr));
        }
    }

    public FatturaResponse salvaFattura(NewFatturaDTO payload) {
        Cliente cliente = clienteService.findById(UUID.fromString(payload.idCliente()));
        LocalDate data = clienteService.getData(payload.data());
        Fattura fat = new Fattura(data, payload.importo(), cliente);
        List<Stato> st = statoRepo.findByNomeStato("da pagare");
        Stato stato;

        if (st.isEmpty()) {
            Stato staToSave = new Stato("da pagare");
            stato = statoRepo.save(staToSave);
        } else {
            stato = st.getFirst();
        }

        Fattura fatSaved = fatturaRepo.save(fat);
        StatoFattura staFat = new StatoFattura(stato, fatSaved);
        statoFatturaRepo.save(staFat);

        FatturaResponse fatRes = new FatturaResponse(fatSaved.getNumeroFattura(), fatSaved.getData(), fatSaved.getImporto(), stato.getNomeStato(), cliente.getUuid(), cliente.getNomeContatto());
        return fatRes;

    }

    public void eliminaFattura(long numeroFattura) {
        Fattura found = this.findById(numeroFattura);
        List<StatoFattura> staFats = statoFatturaRepo.findByFattura(found);
        if (!staFats.isEmpty()) {
            for (int i = 0; i < staFats.size(); i++) {
                statoFatturaRepo.delete(staFats.get(i));
            }
        }
        fatturaRepo.delete(found);
    }
}
