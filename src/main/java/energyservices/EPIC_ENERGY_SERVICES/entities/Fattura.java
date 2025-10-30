package energyservices.EPIC_ENERGY_SERVICES.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor

@Data
@Table(name = "fatture")
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long numeroFattura;
    private LocalDate data;
    private double importo;


    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Fattura(LocalDate data, double importo, Cliente cliente) {
        this.data = data;
        this.importo = importo;
        this.cliente = cliente;
    }

}
