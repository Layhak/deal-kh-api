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

        if(discountFilter.getDiscountValue() > 0) {
            Predicate discountValue = discount.get("discountValue").in(discountFilter.getDiscountValue());
            predicates.add(discountValue);
        }

        //return cb.and(predicates.toArray(new Predicate[0]));
        return criteria.and(predicates.toArray(Predicate[]::new));
    }
}
