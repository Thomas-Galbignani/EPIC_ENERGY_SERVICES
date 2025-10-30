package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import jakarta.validation.constraints.NotBlank;

public record RuoloDto(
        @NotBlank
        String nomeRuolo) {
}
