package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewClientePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.ClienteResponse;
import energyservices.EPIC_ENERGY_SERVICES.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

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

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

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
}
