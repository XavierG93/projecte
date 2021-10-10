package gerisoft.apirest.specifications;

import gerisoft.apirest.entity.*;
import gerisoft.apirest.utils.DateUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gurrea
 */
/*
Filtre de la entitat User, en aquest cas fem dos arrays
un que cerqui els atributs que son string i els detecti
i un altre que cerqui els atributs date i els detecti amb les paraules clau
 */
public class UserSpecification extends CommonSpecificaton implements Specification<User> {

    private List<String> stringFilters;//filtre de atributs
    private List<String> dateFilters;//filtre de dates

    //Especifiquem els filtres de activities
    public UserSpecification(Map<String, String> params) {
        super(params);
        stringFilters = new ArrayList();
        dateFilters = new ArrayList();
        //registrem els filtres que es poden detectar que siguin string
        stringFilters.add("ciutat");
        stringFilters.add("codiPostal");
        stringFilters.add("direccio");
        stringFilters.add("pais");
        stringFilters.add("cogmon1");
        stringFilters.add("cogmon2");
        stringFilters.add("dni");
        stringFilters.add("email");
        stringFilters.add("nom");
        stringFilters.add("telefon");
        stringFilters.add("username");
        stringFilters.add("role.id");
        stringFilters.add("role.name");
        //registrem els filtres que es poden detectar que siguin date, afegim les paraules clau fins i des per a detectarlos
        dateFilters.add("dataNaixement.fins");
        dateFilters.add("dataNaixement.des");
        dateFilters.add("dataNaixement");

    }
    //Cridem a aquest metode per especificar les condicions, es un metode implementat de CommonSpecification
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        /*
        Root permet entrar en els atributs introduits a Specification, en aquest cas UserSpecification, despres CriteriaBuilder permet construir parts de la consulta sql, en aquest cas el WHERE
         */
        /*
        Els join ens permeten introduir-nos a els valors de les taules que introduim, en aquest cas amb atencions i user
         */
        final Join<User, Role> roleJoin = root.join("role", JoinType.INNER);
        List<Predicate> predicates = new ArrayList<>();
        //Iterem sobre els valors del stringFilter anterior
        stringFilters.forEach(filterKey -> {
            String filter = getFilterValue(filterKey);
            if (filter != null) {
                String[] dbKeys = filterKey.split("\\.");
                Predicate filterPredicate;
                switch (dbKeys[0]) {
                    case "role":
                        //Fem un switch per a derivar segons la taula que haguem introduit a la cerca
                        filterPredicate = criteriaBuilder.equal(roleJoin.get(dbKeys[1]), filter);
                        break;
                    default:
                        //cerca a la arrel de la taula un valor que contingui part del string que hem posat a la taula
                        filterPredicate = criteriaBuilder.like(root.get(filterKey), "%" + filter + "%");
                }
                predicates.add(filterPredicate);
            }
        });
        //Iterem sobre els valors del dateFilter anterior
        dateFilters.forEach(filterKey -> {
            Date filter = DateUtil.parseToDate(getFilterValue(filterKey));
            if (filter != null) {
                String[] dbKeys = filterKey.split("\\.");//separem per saber el valor separat per el punt
                Predicate filterPredicate;
                if ("fins".equals(dbKeys[1])) {//si es fins fem que cercui menys del valor de la data i si no el contrari
                    filterPredicate = criteriaBuilder.lessThan(root.get(dbKeys[0]), filter);
                } else {
                    filterPredicate = criteriaBuilder.greaterThan(root.get(dbKeys[0]), filter);
                }
                predicates.add(filterPredicate);
            }
        });
        if (predicates.size() > 0) {
             //Construim la cerca
            /*
            En aquesta part fem que es construeixi la part del where, anidem tant els atributs com els joins per a les taules corresponents
            en aquest cas unicament seria role 
            */
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        //Return per defecte per si es null
        return criteriaBuilder.conjunction();
    }
}
