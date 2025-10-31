package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.exceptions.ValidazioneFallitaExeption;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewIndirizzoPayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.IndirizzoResDTO;
import energyservices.EPIC_ENERGY_SERVICES.services.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public IndirizzoResDTO creaIndirizzo(@RequestBody @Validated NewIndirizzoPayload body, BindingResult valRes, @PathVariable UUID id) {
        if (valRes.hasErrors()) {
            List<String> errList = new ArrayList<>();
            for (int i = 0; i < valRes.getFieldErrors().size(); i++) {
                errList.add(valRes.getFieldErrors().get(i).getDefaultMessage());
            }
            throw new ValidazioneFallitaExeption(errList);
        }
        IndirizzoResDTO i = indirizzoService.SalvaIndirizzo(body, id);
        return i;

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaIndirizzo(@PathVariable UUID id) {
        indirizzoService.cancellaIndirizzo(id);
    }
/*
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

 */
}
