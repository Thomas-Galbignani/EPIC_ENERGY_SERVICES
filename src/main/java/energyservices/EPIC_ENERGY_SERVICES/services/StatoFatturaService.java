package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.BadRequestException;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ClienteRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.StatoFatturaRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.StatoRepo;
import energyservices.EPIC_ENERGY_SERVICES.specifications.StatoFatturaSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatoFatturaService {
    @Autowired
    private StatoFatturaRepo statoFatturaRepo;
    @Autowired
    private ClienteRepo clienteRepo;
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

    public Page<StatoFattura> filtraFatture(
            int page, int size,
            String idcliente,
            String nomeStato,
            String dataDa,
            String dataA,
            Double impMin,
            Double impMax
    ) {
        LocalDate dataIn;
        if (dataDa.equals("0")) {
            dataIn = null;
        } else {
            try {
                dataIn = getData(dataDa);
            } catch (Exception e) {
                dataIn = null;
            }
        }
        LocalDate dataFin;
        if (dataA.equals("0")) {
            dataFin = null;
        } else {
            try {
                dataFin = getData(dataA);
            } catch (Exception e) {
                dataFin = null;
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
        Stato stato;
        if (nomeStato.equals("0")) {
            stato = null;
        } else {
            List<Stato> stList = statoRepo.findByNomeStato(nomeStato);
            if (stList.isEmpty()) {
                stato = null;
            } else {
                stato = stList.getFirst();
            }
        }
        Pageable pageable = PageRequest.of(page, size);
        return statoFatturaRepo.findAll((StatoFatturaSpec.filtra(cliente, stato, dataIn, dataFin, impMin, impMax)), pageable);


    }
}
