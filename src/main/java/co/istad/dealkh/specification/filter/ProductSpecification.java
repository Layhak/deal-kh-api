package co.istad.dealkh.specification.filter;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductSpecification implements Specification<Product> {

    private final ProductFilter productFilter;
    List<Predicate> predicates = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<Product> product, CriteriaQuery<?> query, CriteriaBuilder criteria) {

        if (productFilter.getName() != null) {
            Predicate name = criteria.like(criteria.upper(product.get("name")),productFilter.getName().toUpperCase() + "%");
            predicates.add(name);
        }

        if (productFilter.getDiscountPercentage() >= 0) {
            Predicate discountPercentage = product.join("discount").get("discountPercentage").in(productFilter.getDiscountPercentage());
            predicates.add(discountPercentage);
        }

        if (productFilter.getCategory() != null) {
            Predicate category = product.get("category").in(productFilter.getCategory());
            predicates.add(category);
        }

        return criteria.and(predicates.toArray(Predicate[]::new));
    }
}
