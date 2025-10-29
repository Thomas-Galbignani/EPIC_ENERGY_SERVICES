package energyservices.EPIC_ENERGY_SERVICES.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Comune;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.Indirizzo;
import energyservices.EPIC_ENERGY_SERVICES.enums.RagioneSociale;
import energyservices.EPIC_ENERGY_SERVICES.enums.TipoSede;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.BadRequestException;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.NotFoundException;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewClientePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NuovoClientePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.ClienteResDTO;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.ClienteResDataDTO;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.ClienteResLogoDTO;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ClienteRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.FatturaRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.IndirizzoRepo;
import energyservices.EPIC_ENERGY_SERVICES.specifications.SpecificationCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class ClienteService {
    private static final long MAX_SIZE = 10 * 1024 * 1024; //10 Mb ;
    private static final List<String> ALLOWED_TYPES = List.of("image/jpg", "image/jpeg", "image/png");

    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private ComuneService comuneService;
    @Autowired
    private IndirizzoRepo indirizzoRepo;
    @Autowired
    private FatturaRepo fatturaRepo;
    @Autowired
    private FatturaService fatturaService;
    @Autowired
    private Cloudinary imageUploader;


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

    public Cliente findById(UUID uuid) {
        Optional<Cliente> found = clienteRepo.findById(uuid);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new NotFoundException(uuid);
        }
    }

    public ClienteResDTO clienteSave(NuovoClientePayload payloadCliente) {

        String ragioneSociale = payloadCliente.ragSocialeCliente();
        RagioneSociale ragioneSociale1;
        switch (ragioneSociale.toUpperCase()) {
            case "PA":
                ragioneSociale1 = RagioneSociale.PA;
                break;
            case "SAS":
                ragioneSociale1 = RagioneSociale.SAS;
                break;
            case "SPA":
                ragioneSociale1 = RagioneSociale.SPA;
                break;
            case "SRL":
                ragioneSociale1 = RagioneSociale.SRL;
                break;
            default:
                throw new BadRequestException("Ragione sociale non valida, scegli tra queste: PA, SAS, SPA, SRL ");
        }
        Cliente c = new Cliente(ragioneSociale1,
                payloadCliente.piva(),
                payloadCliente.email(),
                LocalDate.now(),
                LocalDate.now(),
                payloadCliente.pec(),
                payloadCliente.telefono(),
                payloadCliente.emailContatto(),
                payloadCliente.nomeContatto(),
                payloadCliente.cognomeContatto(),
                payloadCliente.tellContatto());


        String tipoSedeString = payloadCliente.tipoSede();
        TipoSede tipoSede;
        switch (tipoSedeString.toLowerCase()) {
            case "sede legale":
                tipoSede = TipoSede.SEDE_LEGALE;
                break;
            case "sede operativa":
                tipoSede = TipoSede.SEDE_OPERATIVA;
                break;
            default:
                throw new BadRequestException("Tipo sede non valido, scegli tra le seguenti : 'sede legale', 'sede operativa' ");
        }

        String nomeComune = payloadCliente.denominazione();
        Comune comune = comuneService.findById(nomeComune);

        Cliente cliente = clienteRepo.save(c);

        Indirizzo i = new Indirizzo(payloadCliente.via(), payloadCliente.civico(), payloadCliente.cap(), tipoSede, comune, cliente);

        indirizzoRepo.save(i);

        ClienteResDTO res = new ClienteResDTO(cliente.getUuid(), cliente.getNomeContatto(), cliente.getCognomeContatto());
        return res;

    }

    public void cancellaCliente(UUID id) {
        Cliente found = this.findById(id);
        List<Indirizzo> indirizzi = indirizzoRepo.findByCliente(found);
        if (!indirizzi.isEmpty()) {
            for (int i = 0; i < indirizzi.size(); i++) {
                indirizzoRepo.delete(indirizzi.get(i));
            }
        }
        List<Fattura> fatture = fatturaRepo.findByCliente(found);
        if (!fatture.isEmpty()) {
            for (int i = 0; i < fatture.size(); i++) {
                fatturaService.eliminaFattura(fatture.get(i).getNumeroFattura());
            }
        }
        clienteRepo.delete(found);
    }

    public ClienteResDataDTO setDataUltimoCont(UUID id, String data) {
        Cliente found = this.findById(id);
        LocalDate dataUltCont = this.getData(data);
        found.setDataUltimoContatto(dataUltCont);
        clienteRepo.save(found);
        ClienteResDataDTO res = new ClienteResDataDTO(found.getUuid(), found.getNomeContatto(), found.getCognomeContatto(), found.getDataUltimoContatto());
        return res;

    }

    public ClienteResDataDTO editCliente(UUID id, NewClientePayload newClientePayload) {
        Cliente found = this.findById(id);

        String ragioneSociale = newClientePayload.getRagioneSociale();
        RagioneSociale ragioneSociale1;
        switch (ragioneSociale.toUpperCase()) {
            case "PA":
                ragioneSociale1 = RagioneSociale.PA;
                break;
            case "SAS":
                ragioneSociale1 = RagioneSociale.SAS;
                break;
            case "SPA":
                ragioneSociale1 = RagioneSociale.SPA;
                break;
            case "SRL":
                ragioneSociale1 = RagioneSociale.SRL;
                break;
            default:
                throw new BadRequestException("Ragione sociale non valida, scegli tra queste: PA, SAS, SPA, SRL ");
        }
        found.setRagioneSociale(ragioneSociale1);
        found.setEmail(newClientePayload.getEmail());
        found.setPEC(newClientePayload.getPEC());
        found.setTelefono(newClientePayload.getTelefono());
        found.setEmailContatto(newClientePayload.getEmailContatto());
        found.setNomeContatto(newClientePayload.getNomeContatto());
        found.setCognomeContatto(newClientePayload.getCognomeContatto());
        found.setTelefonoContatto(newClientePayload.getTelefonoContatto());

        clienteRepo.save(found);
        return new ClienteResDataDTO(found.getUuid(), found.getNomeContatto(), found.getCognomeContatto(), found.getDataUltimoContatto());
    }

    public ClienteResLogoDTO changeLogo(UUID id, MultipartFile file) {

        Cliente found = this.findById(id);

        //controllo che il file non sia vuoto
        if (file.isEmpty()) {
            throw new BadRequestException("il file è vuoto");
        }
        //non superi i 10 MB
        if (file.getSize() > MAX_SIZE) {
            throw new BadRequestException("il file supera la dimensione massima consentita");
        }
        //sia di tipo jpg, jpeg o png
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new BadRequestException("formato del file non supportato");
        }
        try {
            Map res = imageUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = (String) res.get("url");
            found.setLogoAziendale(imgUrl);
            clienteRepo.save(found);
            ClienteResLogoDTO response = new ClienteResLogoDTO(found.getUuid().toString(), found.getLogoAziendale());
            return response;

        } catch (IOException ex) {
            throw new BadRequestException("errore nell'upload");
        }
    }

    public Page<Cliente> filtraClienti(int page, int size, String dataIns, String nome) {
        LocalDate data;
        if (dataIns.equals("0")) {
            data = null;

        } else {
            data = getData(dataIns);
        }

        Specification<Cliente> spec = Specification.not(SpecificationCliente.dataInserimentoDopo(data))
                .and(SpecificationCliente.nomeContiene(nome));
        Pageable pageable = PageRequest.of(page, size);

        return clienteRepo.findAll(spec, pageable);

    }


}
