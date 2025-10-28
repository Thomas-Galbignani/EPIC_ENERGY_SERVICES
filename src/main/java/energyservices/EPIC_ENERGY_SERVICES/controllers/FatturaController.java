package energyservices.EPIC_ENERGY_SERVICES.controllers;
/*
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewFatturaPayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.FatturaResponse;
import energyservices.EPIC_ENERGY_SERVICES.services.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @PostMapping
    public ResponseEntity<FatturaResponse> createFattura(
            @RequestBody @Validated NewFatturaPayload payload,
            BindingResult br) {
        if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
        FatturaResponse saved = fatturaService.create(payload);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<FatturaResponse>> listAll() {
        return ResponseEntity.ok(fatturaService.findAll());
    }

    @GetMapping("/{numeroFattura}")
    public ResponseEntity<FatturaResponse> getById(@PathVariable long numeroFattura) {
        return ResponseEntity.ok(fatturaService.findById(numeroFattura));
    }

    @PutMapping("/{numeroFattura}")
    public ResponseEntity<FatturaResponse> update(
            @PathVariable long numeroFattura,
            @RequestBody @Validated NewFatturaPayload payload,
            BindingResult br) {
        if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
        return ResponseEntity.ok(fatturaService.update(numeroFattura, payload));
    }

    @DeleteMapping("/{numeroFattura}")
    public ResponseEntity<Void> delete(@PathVariable long numeroFattura) {
        fatturaService.delete(numeroFattura);
        return ResponseEntity.noContent().build();
    }
}
*/