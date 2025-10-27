package energyservices.EPIC_ENERGY_SERVICES.entities;


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

    @ManyToOne
    @JoinColumn(name = "progressivoComune")
    private Comune comune;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
