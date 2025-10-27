package energyservices.EPIC_ENERGY_SERVICES.entities;


import energyservices.EPIC_ENERGY_SERVICES.enums.StatoFattura;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "fatture")
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long numeroFattura;
    private LocalDate data;
    private double importo;
    private StatoFattura statoFattura;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

}
