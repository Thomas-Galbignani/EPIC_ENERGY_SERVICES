package energyservices.EPIC_ENERGY_SERVICES.entities;


import energyservices.EPIC_ENERGY_SERVICES.enums.RagioneSociale;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private RagioneSociale ragioneSociale;
    private String pIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private String PEC;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale = "";//url cloudinary

    public Cliente(RagioneSociale ragioneSociale, String pIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, String PEC, String telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto) {
        this.ragioneSociale = ragioneSociale;
        this.pIva = pIva;
        this.email = email;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.PEC = PEC;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;

    }


}
