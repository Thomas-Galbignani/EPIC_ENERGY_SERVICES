package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stati")

@NoArgsConstructor
@Data
public class Stato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String nomeStato;

    public Stato(String nomeStato) {
        this.nomeStato = nomeStato;
    }


}
