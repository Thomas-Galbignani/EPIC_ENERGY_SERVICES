package energyservices.EPIC_ENERGY_SERVICES.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "province")
public class Provincia {
    @Id
    @Setter(AccessLevel.NONE)
    private String provincia;
    private String sigla;
    private String nomeRegione;


}
