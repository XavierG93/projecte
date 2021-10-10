package gerisoft.apirest.specifications;

import gerisoft.apirest.entity.Residents;
import gerisoft.apirest.utils.DateUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Gurrea
 */
/*
Filtre de la entitat residents, en aquest cas fem dos arrays
un que cerqui els atributs que son string i els detecti
i un altre que cerqui els atributs date i els detecti amb les paraules clau
*/
public class ResidentsSpecification extends CommonSpecificaton implements Specification<Residents> {

    private List<String> stringFilters;
    private List<String> dateFilters;

    public ResidentsSpecification(Map<String, String> params) {
        super(params);
        stringFilters = new ArrayList();
        dateFilters = new ArrayList();
        stringFilters.add("nom");
        stringFilters.add("cognom1");
        stringFilters.add("cognom2");
        stringFilters.add("dni");
        dateFilters.add("dataBaixa.des");
        dateFilters.add("dataIngres.des");
        dateFilters.add("dataNaixement.des");
        dateFilters.add("dataBaixa.fins");
        dateFilters.add("dataIngres.fins");
        dateFilters.add("dataNaixement.fins");
    }

    @Override
    public Predicate toPredicate(Root<Residents> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        stringFilters.forEach(filterKey -> {
            String filter = getFilterValue(filterKey);
            if (filter != null) {
                Predicate filterPredicate = criteriaBuilder.like(root.get(filterKey), "%"+filter+"%");
                predicates.add(filterPredicate);
            }
        });

        dateFilters.forEach(filterKey -> {
            Date filter = DateUtil.parseToDate(getFilterValue(filterKey));
            if (filter != null) {
                String[] dbKeys = filterKey.split("\\.");
                Predicate filterPredicate;
                if ("fins".equals(dbKeys[1])) {
                    filterPredicate = criteriaBuilder.lessThan(root.get(dbKeys[0]), filter);
                } else {
                    filterPredicate = criteriaBuilder.greaterThan(root.get(dbKeys[0]), filter);
                }
                predicates.add(filterPredicate);
            }
        });


        if (predicates.size() > 0) {
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        return criteriaBuilder.conjunction();
    }
}
