package energyservices.EPIC_ENERGY_SERVICES.payloads.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewClientePayload {

    @NotNull
    private String ragioneSociale;
    @NotBlank
    private String pIva;
    @Email
    private String email;
    private String dataInserimento;
    private String dataUltimoContatto;
    private String PEC;
    private String telefono;
    @Email
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale; // URL Cloudinary


    // Getters e Setters
    // (puoi usare @Data di Lombok se preferisci)
}
