package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stati")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String nomeStato;


}
