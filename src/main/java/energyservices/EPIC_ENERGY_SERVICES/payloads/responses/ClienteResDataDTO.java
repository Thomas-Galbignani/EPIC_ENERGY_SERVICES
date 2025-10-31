package energyservices.EPIC_ENERGY_SERVICES.payloads.responses;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResDataDTO(
        UUID id,
        String nome,
        String cognome,
        LocalDate ultimoContatto
) {
}
