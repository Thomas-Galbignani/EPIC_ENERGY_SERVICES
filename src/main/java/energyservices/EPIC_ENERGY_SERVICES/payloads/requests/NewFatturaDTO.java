package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewFatturaDTO(
        @NotNull
        String data,
        @NotNull
        double importo,
        @NotBlank
        String idCliente
) {
}
