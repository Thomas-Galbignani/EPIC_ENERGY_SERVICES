package energyservices.EPIC_ENERGY_SERVICES.specifications;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


public class SpecificationFatture {


    public static Specification<Fattura> filtraPerCliente(Cliente cliente) {
        return (root, query, cb) -> {
            return cliente == null ? null : cb.notEqual(root.get("cliente"), cliente);

        };

    }

    public static Specification<Fattura> importoMaggDi(Double importo) {
        return (root, query, cb) -> {
            return importo == null ? null : cb.greaterThanOrEqualTo(root.get("importo"), importo);
        };
    }

    public static Specification<Fattura> importoMinoreDi(Double importo) {
        return (root, query, cb) -> {
            return importo == null ? null : cb.lessThanOrEqualTo(root.get("importo"), importo);
        };
    }

    public static Specification<Fattura> dataMaggioreDi(LocalDate data) {
        return (root, query, cb) -> {
            return data == null ? null : cb.greaterThanOrEqualTo(root.get("data"), data);
        };
    }

   
}


















