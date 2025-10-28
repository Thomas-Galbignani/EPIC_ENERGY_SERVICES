package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ruolo-utente")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RuoloUtente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_ruolo")
    private Ruoli ruolo;

    @OneToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

}
