package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ruoli")

@NoArgsConstructor
@Data
public class Ruoli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long id;
    private String nomeRuolo;

    @OneToMany
    @JoinTable(
            name = "ruoli_utenti",
            joinColumns = @JoinColumn(name = "ruolo_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utente> utenti;

    public Ruoli(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }


}
