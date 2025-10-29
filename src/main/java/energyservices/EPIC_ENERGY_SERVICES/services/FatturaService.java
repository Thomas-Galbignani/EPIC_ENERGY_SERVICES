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
import energyservices.EPIC_ENERGY_SERVICES.specifications.SpecificationFatture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public FatturaResponse setStato(long numeroFattura, String nuovoStato) {
        Fattura found = this.findById(numeroFattura);
        List<StatoFattura> staFattura = statoFatturaRepo.findByFattura(found);
        List<Stato> stato = statoRepo.findByNomeStato(nuovoStato);
        if (stato.isEmpty()) {
            throw new BadRequestException("Il nome dello stato non è valido!");
        }
        if (!staFattura.isEmpty()) {
            for (int i = 0; i < staFattura.size(); i++) {
                statoFatturaRepo.delete(staFattura.get(i));
            }
        }

        StatoFattura newState = new StatoFattura(stato.getFirst(), found);
        statoFatturaRepo.save(newState);
        FatturaResponse res = new FatturaResponse(found.getNumeroFattura(), found.getData(), found.getImporto(), stato.getFirst().getNomeStato(), found.getCliente().getUuid(), found.getCliente().getNomeContatto());
        return res;
    }

    public Page<Fattura> filtraFatture(int page, int size, String idcliente, String dataMagDi, Double importoMagDi, Double importoMinDi) {
        LocalDate data;
        if (dataMagDi.equals("0")) {
            data = null;
        } else {
            try {
                data = getData(dataMagDi);
            } catch (Exception e) {
                data = null;
            }
        }
        Cliente cliente;
        if (!idcliente.equals("0")) {
            Optional<Cliente> found = clienteRepo.findById(UUID.fromString(idcliente));

            if (found.isPresent()) {
                cliente = found.get();
            } else {
                cliente = null;
            }
        } else {
            cliente = null;
        }
        Specification<Fattura> spec = Specification.not(SpecificationFatture.filtraPerCliente(cliente))
                .and(SpecificationFatture.dataMaggioreDi(data))
                .and(SpecificationFatture.importoMaggDi(importoMagDi))
                .and(SpecificationFatture.importoMinoreDi(importoMinDi));
        Pageable pageable = PageRequest.of(page, size);
        return fatturaRepo.findAll(spec, pageable);
    }
}
