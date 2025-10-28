package energyservices.EPIC_ENERGY_SERVICES.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FatturaResponse {
    private long numeroFattura;
    private LocalDate data;
    private double importo;
    private String statoFattura;
    private UUID clienteId;
    private String nomeContattoCliente;
}
