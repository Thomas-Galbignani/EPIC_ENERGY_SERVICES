package energyservices.EPIC_ENERGY_SERVICES.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comuni")
public class Comune {
    @Id
    @Setter(AccessLevel.NONE)
    private String denominazione;
    private long progressivoComune;
    private long codiceProvincia;

    @ManyToOne
    @JoinColumn(name = "nomeProvincia")
    private Provincia provincia;
}
