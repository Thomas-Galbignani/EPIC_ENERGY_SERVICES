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
    @NotBlank
    private String ragioneSociale;
    @Email
    private String email;
    @NotNull
    private String PEC;
    private String telefono;
    private String emailContatto;
    @NotBlank
    private String nomeContatto;
    @NotBlank
    private String cognomeContatto;
    private String telefonoContatto;
}
