package energyservices.EPIC_ENERGY_SERVICES.specifications;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


public class SpecificationCliente {


    public static Specification<Cliente> dataInserimentoDopo(LocalDate data) {

        return (root, query, cb) ->
                data == null ? null : cb.lessThan(root.get("dataInserimento"), data);
    }

    public static Specification<Cliente> nomeContiene(String nome) {
        return (root, query, cb) ->
                (nome == null || nome.isBlank()) ? null :
                        cb.like(cb.lower(root.get("nomeContatto")), "%" + nome.toLowerCase() + "%");
    }


}
