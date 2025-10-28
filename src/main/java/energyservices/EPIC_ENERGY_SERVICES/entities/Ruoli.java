package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ruoli_utente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ruoli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long id;
    private String nomeRuolo;
    

}
