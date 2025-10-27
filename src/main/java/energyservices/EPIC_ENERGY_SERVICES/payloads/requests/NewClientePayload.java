package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import energyservices.EPIC_ENERGY_SERVICES.enums.RagioneSociale;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewClientePayload {

    @NotNull
    private RagioneSociale ragioneSociale;

    @NotBlank
    private String pIva;

    @Email
    private String email;

    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;

    @PositiveOrZero
    private long fatturatoAnnuale;

    private String PEC;
    private String telefono;

    @Email
    private String emailContatto;

    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;

    private String logoAziendale; // URL Cloudinary

    // ID degli indirizzi opzionali
    private UUID sedeLegaleId;
    private UUID sedeOperativaId;

    // Getters e Setters
    // (puoi usare @Data di Lombok se preferisci)
}
