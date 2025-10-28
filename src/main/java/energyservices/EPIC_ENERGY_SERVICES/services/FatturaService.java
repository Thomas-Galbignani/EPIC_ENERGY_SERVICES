package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.BadRequestException;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.NotFoundException;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewFatturaDTO;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.FatturaResponse;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ClienteRepo;
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
    private ClienteRepo clienteRepo;
    @Autowired
    private StatoFatturaRepo statoFatturaRepo;
    @Autowired
    private StatoRepo statoRepo;

    private LocalDate getData(String data) {
        String dataString = "";
        if (data.length() > 10) {
            dataString = data.substring(0, 10);
        } else if (data.length() == 10) {
            dataString = data;
        } else {
            throw new BadRequestException("data non valida");
        }
        try {
            String[] dataArray = dataString.split("-");
            int anno = Integer.parseInt(dataArray[0]);
            int mese = Integer.parseInt(dataArray[1]);
            int giorno = Integer.parseInt(dataArray[2]);
            LocalDate dataloc = LocalDate.of(anno, mese, giorno);
            return dataloc;
        } catch (Exception ex) {
            throw new BadRequestException("data non valida");
        }
    }

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

        Optional<Cliente> opCl = clienteRepo.findById(UUID.fromString(payload.idCliente()));
        Cliente cliente;
        if (opCl.isPresent()) {
            cliente = opCl.get();
        } else {
            throw new NotFoundException(UUID.fromString(payload.idCliente()));
        }

        Fattura fat = new Fattura(LocalDate.now(), payload.importo(), cliente);
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
