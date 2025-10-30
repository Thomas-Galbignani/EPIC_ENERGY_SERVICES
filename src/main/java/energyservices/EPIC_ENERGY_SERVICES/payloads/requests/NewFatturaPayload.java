package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

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
