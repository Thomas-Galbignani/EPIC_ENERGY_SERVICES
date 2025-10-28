package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.entities.Utente;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.RegisterUtentePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.UtenteResponsePayload;
import energyservices.EPIC_ENERGY_SERVICES.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /*
        @PostMapping("/login")
        public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest body) {
            String token = authService.loginAndGetToken(body);
            return ResponseEntity.ok(new JwtAuthResponse(token));
        }
    */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteResponsePayload register(@RequestBody @Validated RegisterUtentePayload payload, BindingResult br) {
        if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
        Utente u = authService.salvaUtente(payload);
        UtenteResponsePayload res = new UtenteResponsePayload(u.getUuid().toString(), u.getName(), u.getSurname());
        return res;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(authService.findById(id));
    }


}
