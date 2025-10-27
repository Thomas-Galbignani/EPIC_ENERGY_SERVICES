package energyservices.EPIC_ENERGY_SERVICES.payloads.responses;

import energyservices.EPIC_ENERGY_SERVICES.enums.RagioneSociale;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private UUID uuid;
    private RagioneSociale ragioneSociale;
    private String pIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private long fatturatoAnnuale;
    private String nomeContatto;
    private String cognomeContatto;
    private String logoAziendale;
}
