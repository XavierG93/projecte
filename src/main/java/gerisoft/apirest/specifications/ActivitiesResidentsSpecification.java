package gerisoft.apirest.specifications;

import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.entity.ActivitiesResidents;
import gerisoft.apirest.entity.User;
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
filtre on establim els valors a cercar per part del codi a dins de 
la url de cerca
 */
public class ActivitiesResidentsSpecification extends CommonSpecificaton implements Specification<ActivitiesResidents> {

    private List<String> stringFilters;//filtre de atributs
    private List<String> dateFilters;//filtre de dates
    //Especifiquem els filtres de activities

    public ActivitiesResidentsSpecification(Map<String, String> params) {
        super(params);
        stringFilters = new ArrayList();
        dateFilters = new ArrayList();
        //registrem els filtres que es poden detectar que siguin string
        stringFilters.add("observacion");
        stringFilters.add("user.id");
        stringFilters.add("user.username");
        stringFilters.add("user.dni");
        stringFilters.add("resident.id");
        stringFilters.add("activities.id");
        stringFilters.add("activities.name");
        //registrem els filtres que es poden detectar que siguin date, afegim les paraules clau fins i des per a detectarlos
        dateFilters.add("dataActiviti.fins");
        dateFilters.add("dataActiviti.des");
    }
//Cridem a aquest metode per especificar les condicions, es un metode implementat de CommonSpecification

    @Override
    public Predicate toPredicate(Root<ActivitiesResidents> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        /*
        Root permet entrar en els atributs introduits a Specification, en aquest cas ActivitiesResidents, despres CriteriaBuilder permet construir parts de la consulta sql, en aquest cas el WHERE
         */
 /*
        Els join ens permeten introduir-nos a els valors de les taules que introduim, en aquest cas amb activities i user
         */
        final Join<ActivitiesResidents, Activities> activitiesJoin = root.join("activitie", JoinType.INNER);
        final Join<ActivitiesResidents, User> userJoin = root.join("user", JoinType.INNER);
        
        //Una llista de filtres que generem mes abaix
        List<Predicate> predicates = new ArrayList<>();
        //Iterem sobre els valors del stringFilter anterior
        stringFilters.forEach(filterKey -> {
            String filter = getFilterValue(filterKey);//Cridem al metode de CommonSpecification per obtindre el valor string
            if (filter != null) {
                String[] dbKeys = filterKey.split("\\.");//dividim el filtre per a veure l'atribut que cerquem si es que n'hi ha
                Predicate filterPredicate;
                switch (dbKeys[0]) {
                    case "activities":
                        //Fem un switch per a derivar segons la taula que haguem introduit a la cerca
                        filterPredicate = criteriaBuilder.equal(activitiesJoin.get(dbKeys[1]), filter);
                        break;
                    case "user":
                        filterPredicate = criteriaBuilder.equal(userJoin.get(dbKeys[1]), filter);
                        break;
                    case "resident":
                        filterPredicate = criteriaBuilder.equal(root.get("resident").get("id"), filter);
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
            Date filter = DateUtil.parseToDate(getFilterValue(filterKey));//obtenim el valor string i el pasem a date
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
            select * from attentionsResidents ar inner join user on user.id= ar.user_id WHERE dbkey[0] per exemple u.username and 
            */
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        //Return per defecte per si es null, retorna tots els valors de la taula attentionsResidents
        return criteriaBuilder.conjunction();

    }
}
