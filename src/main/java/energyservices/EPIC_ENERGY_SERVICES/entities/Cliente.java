package energyservices.EPIC_ENERGY_SERVICES.entities;


import energyservices.EPIC_ENERGY_SERVICES.enums.RagioneSociale;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    private long fatturatoAnnuale;
    private String PEC;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;//url cloudinary
}
