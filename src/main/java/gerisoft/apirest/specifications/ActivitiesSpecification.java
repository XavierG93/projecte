package gerisoft.apirest.specifications;

import gerisoft.apirest.entity.Activities;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Gurrea
 */
/*
Filtre de la entitat activities, en aquest cas nomes hem de detectar l'atribut nom o l'id
*/
public class ActivitiesSpecification extends CommonSpecificaton implements Specification<Activities> {
/*
    Com que nomes tenim un posible camp, el desem
    */
    private static String QUERY_PARAM_NOM = "name";

    public ActivitiesSpecification(Map<String, String> params) {
        super(params);
    }

    @Override
    public Predicate toPredicate(Root<Activities> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        String filter = getFilterValue(QUERY_PARAM_NOM);
        if (filter != null) {
            Predicate filterPredicate = criteriaBuilder.like(root.get(QUERY_PARAM_NOM), "%"+filter+"%");//Li demanem que cerqui en base al introduit, es a dir, cerca el camp desat mes el text introduit
            predicates.add(filterPredicate);
        }

        if (predicates.size() > 0) {
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        return criteriaBuilder.conjunction();
    }
}
