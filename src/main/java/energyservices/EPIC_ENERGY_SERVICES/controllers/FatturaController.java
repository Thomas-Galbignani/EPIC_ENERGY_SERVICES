package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import energyservices.EPIC_ENERGY_SERVICES.services.FatturaService;
import energyservices.EPIC_ENERGY_SERVICES.services.StatoFatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;
    @Autowired
    private StatoFatturaService statoFatturaService;

    /*
        @PostMapping
        public ResponseEntity<FatturaResponse> createFattura(
                @RequestBody @Validated NewFatturaPayload payload,
                BindingResult br) {
            if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
            FatturaResponse saved = fatturaService.create(payload);
            return ResponseEntity.status(201).body(saved);
        }
    */

    @GetMapping
    public Page<StatoFattura> filtraFatt(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") String idCliente,
            @RequestParam(required = false, defaultValue = "0") String nomeStato,
            @RequestParam(required = false, defaultValue = "0") String dataDa,
            @RequestParam(required = false, defaultValue = "0") String dataA,
            @RequestParam(required = false) Double impMin,
            @RequestParam(required = false) Double impMax

    ) {
        return statoFatturaService.filtraFatture(page, size, idCliente, nomeStato, dataDa, dataA, impMin, impMax);
    }

/*
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
    */
}
