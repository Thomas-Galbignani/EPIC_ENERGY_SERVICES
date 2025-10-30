package energyservices.EPIC_ENERGY_SERVICES.specifications;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import energyservices.EPIC_ENERGY_SERVICES.entities.StatoFattura;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class StatoFatturaSpec {

    public static Specification<StatoFattura> filtra(Cliente cliente, Stato stato, LocalDate dataDa, LocalDate dataA, Double impMin, Double impMax) {
        return (root, query, cb) -> {
            Join<StatoFattura, Fattura> fatturaJoin = root.join("fattura");

            Join<StatoFattura, Stato> statoJoin = root.join("stato");

            Predicate p = cb.conjunction();
            if (cliente != null) {
                p = cb.and(p, cb.equal(fatturaJoin.get("cliente"), cliente));
            }
            if (stato != null) {
                p = cb.and(p, cb.equal(cb.lower(statoJoin.get("nomeStato")), stato.getNomeStato().toLowerCase()));
            }
            if (dataDa != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(fatturaJoin.get("data"), dataDa));
            }
            if (dataA != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(fatturaJoin.get("data"), dataA));
            }
            if (impMax != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(fatturaJoin.get("importo"), impMax));
            }
            if (impMin != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(fatturaJoin.get("importo"), impMin));
            }

            return p;
        };
    }
}
