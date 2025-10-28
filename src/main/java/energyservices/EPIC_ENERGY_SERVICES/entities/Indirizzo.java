package energyservices.EPIC_ENERGY_SERVICES.entities;


import energyservices.EPIC_ENERGY_SERVICES.enums.TipoSede;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "indirizzi")
public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String via;
    private String civico;
    private String cap;
    @Enumerated(EnumType.STRING)
    private TipoSede tipoSede;

    @ManyToOne
    @JoinColumn(name = "comune")
    private Comune comune;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
