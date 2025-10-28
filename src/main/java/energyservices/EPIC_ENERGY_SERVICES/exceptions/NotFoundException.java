package energyservices.EPIC_ENERGY_SERVICES.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("la risorsa con id " + id.toString() + " non è presente nel db");
    }
}
