package energyservices.EPIC_ENERGY_SERVICES.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
