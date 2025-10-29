package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.BadRequestException;
import energyservices.EPIC_ENERGY_SERVICES.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

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