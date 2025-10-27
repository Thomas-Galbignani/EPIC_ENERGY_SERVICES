package energyservices.EPIC_ENERGY_SERVICES.payloads.responses;

import energyservices.EPIC_ENERGY_SERVICES.enums.StatoFattura;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FatturaResponse {
    private long numeroFattura;
    private LocalDate data;
    private double importo;
    private StatoFattura statoFattura;
    private UUID clienteId;
    private String nomeContattoCliente;
}
