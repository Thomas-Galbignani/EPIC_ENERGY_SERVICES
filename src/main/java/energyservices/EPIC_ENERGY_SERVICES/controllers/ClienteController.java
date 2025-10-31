package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.BadRequestException;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.ValidazioneFallitaExeption;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.EmailDTO;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NuovoClientePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.ClienteResDTO;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.ClienteResLogoDTO;
import energyservices.EPIC_ENERGY_SERVICES.services.ClienteService;
import energyservices.EPIC_ENERGY_SERVICES.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private MailService mailService;

    /*
        @PostMapping
        public ResponseEntity<ClienteResponse> createCliente(
                @RequestBody @Validated NewClientePayload payload,
                BindingResult br) {
            if (br.hasErrors()) {
                throw new IllegalArgumentException(br.getAllErrors().toString());
            }
            ClienteResponse saved = clienteService.create(payload);
            return ResponseEntity.status(201).body(saved);
        }
    */
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

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResDTO salvaCliente(@RequestBody @Validated NuovoClientePayload body, BindingResult valRes) {

        if (valRes.hasErrors()) {
            List<String> errList = new ArrayList<>();
            for (int i = 0; i < valRes.getFieldErrors().size(); i++) {
                errList.add(valRes.getFieldErrors().get(i).getDefaultMessage());
            }
            throw new ValidazioneFallitaExeption(errList);
        }
        ClienteResDTO c = clienteService.clienteSave(body);
        return c;

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaCliente(@PathVariable UUID id) {
        clienteService.cancellaCliente(id);
    }


    @GetMapping
    public Page<Cliente> filtraClienti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Double fatMin,
            @RequestParam(required = false) Double fatMax,
            @RequestParam(required = false, defaultValue = "0") String dataInsSt,
            @RequestParam(required = false, defaultValue = "0") String dataUltSt,
            @RequestParam(required = false, defaultValue = "nome") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String direction
    ) {
        return clienteService.filtra(page, size, nome, fatMin, fatMax, dataInsSt, dataUltSt, sortBy, direction);
    }

    @PatchMapping("/{id}/logo")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResLogoDTO changeAvatar(@PathVariable UUID id, @RequestParam("logo") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return clienteService.changeLogo(id, file);
    }

    @PostMapping("/{id}/email")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String inviMail(@PathVariable UUID id, @RequestBody @Validated EmailDTO body) {
        mailService.inviaMail(id, body.oggetto(), body.messaggio());
        return "messaggio inviato";

    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(
            @PathVariable UUID id,
            @RequestBody @Validated NewClientePayload payload,
            BindingResult br) {
        if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
        return ResponseEntity.ok(clienteService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    */

}