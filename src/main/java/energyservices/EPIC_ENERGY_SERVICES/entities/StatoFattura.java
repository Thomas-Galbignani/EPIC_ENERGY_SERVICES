package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stati_fatture")
@NoArgsConstructor

@Data
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_stato")
    private Stato stato;

    @OneToOne
    @JoinColumn(name = "id_fattura")
    private Fattura fattura;

    public StatoFattura(Stato stato, Fattura fattura) {
        this.stato = stato;
        this.fattura = fattura;

    }


}
