package energyservices.EPIC_ENERGY_SERVICES.specifications;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class SpecificationFatture {
public static Specification<Fattura> filtraPerCliente(Cliente cliente){
    return (root,query,cb) -> {
        return cliente == null ? null : cb.notEqual(root.get("cliente"),cliente);

    };

}



}


















