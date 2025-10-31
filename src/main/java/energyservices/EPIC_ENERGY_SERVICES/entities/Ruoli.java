package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ruoli")

@NoArgsConstructor
@Getter
@Setter
public class Ruoli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long id;
    private String nomeRuolo;

    @ManyToMany
    @JoinTable(
            name = "ruoli_utenti",
            joinColumns = @JoinColumn(name = "ruolo_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utente> utenti;

    public Ruoli(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }

    @Override
    public String toString() {
        return "Ruoli{" +
                "id=" + id +
                ", nomeRuolo='" + nomeRuolo + '\'' +
                '}';
    }
}
