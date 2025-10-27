package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import energyservices.EPIC_ENERGY_SERVICES.enums.StatoFattura;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFatturaPayload {

    @NotNull
    private LocalDate data;

    @Positive
    private double importo;

    @NotNull
    private StatoFattura statoFattura;

    @NotNull
    private UUID clienteId;

}
