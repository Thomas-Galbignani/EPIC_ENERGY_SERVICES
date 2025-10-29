package energyservices.EPIC_ENERGY_SERVICES.specifications;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import jakarta.persistence.criteria.Predicate;
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

    public static Specification<Cliente> dataUltimoContDopo(LocalDate data) {

        return (root, query, cb) ->
                data == null ? null : cb.lessThan(root.get("dataUltimoContatto"), data);
    }

    public static Specification<Cliente> filtra(String nome, Double fatMin, Double fatMax, LocalDate dataIns, LocalDate dataUlt) {
        return (root, query, cb) -> {
            var subQuery = query.subquery(Double.class);
            var fattRoot = subQuery.from(Fattura.class);
            subQuery.select(cb.sum(fattRoot.get("importo")));
            subQuery.where(cb.equal(fattRoot.get("cliente"), root));
            Predicate p = cb.conjunction();
            if (fatMin != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(subQuery, fatMin));
            }
            if (fatMax != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(subQuery, fatMax));
            }
            if (nome != null && !nome.isBlank()) {
                p = cb.and(p, cb.like(cb.lower(root.get("nomeContatto")), "%" + nome.toLowerCase() + "%"));
            }
            if (dataIns != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("dataInserimento"), dataIns));
            }
            if (dataUlt != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("dataInserimento"), dataUlt));
            }
            return p;

        };

    }


}
