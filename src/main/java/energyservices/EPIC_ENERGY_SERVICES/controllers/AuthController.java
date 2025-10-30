package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.entities.Utente;
import energyservices.EPIC_ENERGY_SERVICES.exceptions.ValidazioneFallitaExeption;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.LoginRequest;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.RegisterUtentePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.JwtAuthResponse;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.UtenteResponsePayload;
import energyservices.EPIC_ENERGY_SERVICES.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder bCrypt;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest body) {
        String token = authService.checkAndGenerate(body);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteResponsePayload register(@RequestBody @Validated RegisterUtentePayload payload, BindingResult br) {
        if (br.hasErrors()) {
            List<String> errList = new ArrayList<>();
            for (int i = 0; i < br.getFieldErrors().size(); i++) {
                errList.add(br.getFieldErrors().get(i).getDefaultMessage());
            }
            throw new ValidazioneFallitaExeption(errList);
        }
        RegisterUtentePayload utToSave = new RegisterUtentePayload(payload.getUsername(), payload.getEmail(), bCrypt.encode(payload.getPassword()), payload.getName(), payload.getSurname());
        Utente u = authService.salvaUtente(utToSave);
        UtenteResponsePayload res = new UtenteResponsePayload(u.getUuid().toString(), u.getName(), u.getSurname());
        return res;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(authService.findById(id));
    }


}
