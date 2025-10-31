package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank
        String oggetto,
        @NotBlank
        String messaggio) {


}
