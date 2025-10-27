package energyservices.EPIC_ENERGY_SERVICES.controllers;

import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.RegisterUtentePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.LoginRequest;
import energyservices.EPIC_ENERGY_SERVICES.payloads.responses.JwtAuthResponse;
import energyservices.EPIC_ENERGY_SERVICES.services.AuthService;
import energyservices.EPIC_ENERGY_SERVICES.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest body) {
        String token = authService.loginAndGetToken(body);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterUtentePayload payload, BindingResult br) {
        if (br.hasErrors()) throw new IllegalArgumentException(br.getAllErrors().toString());
        return ResponseEntity.status(201).body(utenteService.register(payload));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(utenteService.findById(id));
    }
}
