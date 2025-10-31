package energyservices.EPIC_ENERGY_SERVICES.entities;


import energyservices.EPIC_ENERGY_SERVICES.enums.TipoSede;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
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

    public Indirizzo(String via, String civico, String cap, TipoSede tipoSede, Comune comune, Cliente cliente) {
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.tipoSede = tipoSede;
        this.comune = comune;
        this.cliente = cliente;
    }
}
