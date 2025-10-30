package energyservices.EPIC_ENERGY_SERVICES.specifications;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.entities.Comune;
import energyservices.EPIC_ENERGY_SERVICES.entities.Fattura;
import energyservices.EPIC_ENERGY_SERVICES.entities.Indirizzo;
import energyservices.EPIC_ENERGY_SERVICES.enums.TipoSede;
import jakarta.persistence.criteria.*;
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
/*
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
    */

    public static Specification<Cliente> filtra(
            String nome,
            Double fatMin,
            Double fatMax,
            LocalDate dataIns,
            LocalDate dataUlt,
            String sortBy,
            String direction
    ) {
        return (root, query, cb) -> {

            Predicate predicato = cb.conjunction();

            //  FILTRI 
            if (nome != null && !nome.isEmpty()) {
                predicato = cb.and(predicato,
                        cb.like(cb.lower(root.get("nomeContatto")), "%" + nome.toLowerCase() + "%"));
            }

            if (dataIns != null) {
                predicato = cb.and(predicato,
                        cb.greaterThanOrEqualTo(root.get("dataInserimento"), dataIns));
            }

            if (dataUlt != null) {
                predicato = cb.and(predicato,
                        cb.lessThanOrEqualTo(root.get("dataUltimoContatto"), dataUlt));
            }

            // Filtri per fatturato usando subquery
            if (fatMin != null || fatMax != null) {
                Subquery<Double> sub = query.subquery(Double.class);
                Root<Fattura> fatturaRoot = sub.from(Fattura.class);
                sub.select(cb.sum(fatturaRoot.get("importo")));
                sub.where(cb.equal(fatturaRoot.get("cliente"), root));

                if (fatMin != null) {
                    predicato = cb.and(predicato,
                            cb.greaterThanOrEqualTo(sub.getSelection(), fatMin));
                }
                if (fatMax != null) {
                    predicato = cb.and(predicato,
                            cb.lessThanOrEqualTo(sub.getSelection(), fatMax));
                }
            }

            //  ORDINAMENTO
            boolean asc = !"DESC".equalsIgnoreCase(direction);
            Order ordine;


            switch (sortBy.toLowerCase()) {

                case "fatturato": {
                    // Subquery per ordinamento
                    Subquery<Double> sub = query.subquery(Double.class);
                    Root<Fattura> fatturaRoot = sub.from(Fattura.class);
                    sub.select(cb.sum(fatturaRoot.get("importo")));
                    sub.where(cb.equal(fatturaRoot.get("cliente"), root));
                    ordine = asc ? cb.asc(sub.getSelection()) : cb.desc(sub.getSelection());
                    break;
                }

                case "provincia": {
                    Subquery<String> sub = query.subquery(String.class);
                    Root<Indirizzo> indirizzoRoot = sub.from(Indirizzo.class);
                    Join<Indirizzo, Comune> comuneJoin = indirizzoRoot.join("comune");

                    //relazione Indirizzo e Cliente
                    sub.select(comuneJoin.get("provincia"));
                    sub.where(
                            cb.equal(indirizzoRoot.get("cliente"), root),
                            cb.equal(indirizzoRoot.get("tipoSede"), TipoSede.SEDE_LEGALE)
                    );

                    ordine = asc ? cb.asc(sub.getSelection()) : cb.desc(sub.getSelection());
                    break;
                }

                case "datainserimento":
                    ordine = asc ? cb.asc(root.get("dataInserimento"))
                            : cb.desc(root.get("dataInserimento"));
                    break;

                case "nome":
                default:
                    ordine = asc ? cb.asc(root.get("nomeContatto")) : cb.desc(root.get("nomeContatto"));
                    break;
            }

            query.orderBy(ordine);

            return predicato;
        };
    }


}
