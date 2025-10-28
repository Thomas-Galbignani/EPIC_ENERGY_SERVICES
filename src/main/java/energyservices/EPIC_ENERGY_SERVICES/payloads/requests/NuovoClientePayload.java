package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuovoClientePayload(
        @NotBlank
        String ragSocialeCliente,
        @NotBlank
        String piva,
        @Email
        String email,
        @NotNull
        String pec,
        String telefono,
        String emailContatto,
        @NotBlank
        String nomeContatto,
        @NotBlank
        String cognomeContatto,
        String tellContatto,
        @NotBlank
        String via,
        @NotBlank
        String civico,
        @NotBlank
        String cap,
        @NotNull
        String denominazione,
        @NotNull
        String tipoSede
) {
}
