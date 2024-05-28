package co.istad.dealkh.specification.filter;

import co.istad.dealkh.domain.Discount;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class DiscountSpecification implements Specification<Discount> {
    private final DiscountFilter discountFilter;
    List<Predicate> predicates = new ArrayList<>();
    @Override
    public Predicate toPredicate(Root<Discount> discount, CriteriaQuery<?> query, CriteriaBuilder criteria) {

        if(discountFilter.getName() != null){
            Predicate name = criteria.like(criteria.upper(discount.get("name")), "%" + discountFilter.getName().toUpperCase() + "%");
            predicates.add(name);
        }

        if(discountFilter.getDiscountPercentage() > 0) {
            Predicate discountPercentage = discount.get("discountPercentage").in(discountFilter.getDiscountPercentage());
            predicates.add(discountPercentage);
        }

        //return cb.and(predicates.toArray(new Predicate[0]));
        return criteria.and(predicates.toArray(Predicate[]::new));
    }
}
