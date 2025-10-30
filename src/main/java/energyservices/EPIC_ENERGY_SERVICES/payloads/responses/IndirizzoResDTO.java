package energyservices.EPIC_ENERGY_SERVICES.payloads.responses;

public record IndirizzoResDTO(
        String idIndirizzo,
        String via,
        String civico,
        String cap,
        String comune,
        String idCliente
) {
}
