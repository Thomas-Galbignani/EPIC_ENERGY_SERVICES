package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewIndirizzoPayload {

    @NotBlank
    private String via;

    @NotBlank
    private String civico;

    @NotBlank
    private String cap;

    @NotNull
    private Long comuneId;

    @NotNull
    private UUID clienteId;

}
