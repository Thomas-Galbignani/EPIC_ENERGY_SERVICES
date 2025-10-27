package energyservices.EPIC_ENERGY_SERVICES.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comuni")
public class Comune {
    @Id
    @Setter(AccessLevel.NONE)
    private long progressivoComune;
    private String denominazione;
    private long codiceProvincia;

    @ManyToOne
    @JoinColumn(name = "nomeProvincia")
    private Provincia provincia;
}
