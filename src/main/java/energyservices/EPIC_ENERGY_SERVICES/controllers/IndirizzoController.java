package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewIndirizzoPayload;
import energyservices.EPIC_ENERGY_SERVICES.services.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/indirizzi")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @PostMapping
    public ResponseEntity<?> createIndirizzo(@RequestBody @Validated NewIndirizzoPayload payload, BindingResult br) {
        if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
        return ResponseEntity.status(201).body(indirizzoService.create(payload));
    }

    @GetMapping
    public ResponseEntity<List<?>> listAll() {
        return ResponseEntity.ok(indirizzoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(indirizzoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Validated NewIndirizzoPayload payload, BindingResult br) {
        if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
        return ResponseEntity.ok(indirizzoService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        indirizzoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
