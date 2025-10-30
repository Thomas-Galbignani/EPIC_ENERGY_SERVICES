package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NewFatturaDTO;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.FatturaResponse;
import energyservices.EPIC_ENERGY_SERVICES.services.FatturaService;
import energyservices.EPIC_ENERGY_SERVICES.services.StatoFatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // Creazione nuova fattura
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public FatturaResponse creaFattura(@RequestBody @Validated NewFatturaDTO payload) {
        return fatturaService.salvaFattura(payload);
    }

    // cerca fattura per ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Fattura getFatturaById (@PathVariable long id) {
        return fatturaService.findById(id);
    }

    // Eliminare una fattura per id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaFattura(@PathVariable long id){
        fatturaService.eliminaFattura(id);
    }

    // Aggiornare stato fattura
    @PatchMapping("/{id}/stato")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public FatturaResponse aggiornaStatoFattura(@PathVariable long id,@RequestParam String nuovoStato){
        return fatturaService.setStato(id, nuovoStato);
    }

    //filtrare/paginare le fatture
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
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
    // Cambiare lo stato della fattura
    @PutMapping("/{numeroFattura}/stato")
    public ResponseEntity<FatturaResponse> cambiaStato(
            @PathVariable long numeroFattura,
            @RequestParam String nuovoStato
    ) {
        FatturaResponse response = fatturaService.setStato(numeroFattura, nuovoStato);
        return ResponseEntity.ok(response);
    }
}