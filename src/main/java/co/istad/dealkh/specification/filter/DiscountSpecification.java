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

public record DiscountSpecification(DiscountFilter discountFilter) implements Specification<Discount> {
    @Override
    public Predicate toPredicate(Root<Discount> discount, CriteriaQuery<?> query, CriteriaBuilder criteria) {
        List<Predicate> predicates = new ArrayList<>();

        if (discountFilter.getDiscountValue() > 0) {
            Predicate discountValue = criteria.equal(discount.get("discountValue"), discountFilter.getDiscountValue());
            predicates.add(discountValue);
        }

        return criteria.and(predicates.toArray(Predicate[]::new));
    }
}
