package co.istad.dealkh.specification.filter;

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
            Predicate name = criteria.like(criteria.upper(product.get("name")), "%" + productFilter.getName().toUpperCase() + "%");
            predicates.add(name);
        }

        if (productFilter.getCategory() != null) {
            Predicate category = criteria.like(criteria.upper(product.join("category").get("name")), productFilter.getCategory().toUpperCase());
            predicates.add(category);
        }

        if (productFilter.getDiscountValue() > 0) {
            Predicate discountValue = criteria.equal(product.join("discount").get("discountValue"), productFilter.getDiscountValue());
            predicates.add(discountValue);
        }

        if (productFilter.getShop() != null) {
            Predicate shop = criteria.like(criteria.upper(product.join("shop").get("name")), "%" + productFilter.getShop().toUpperCase() + "%");
            predicates.add(shop);
        }

        return criteria.and(predicates.toArray(Predicate[]::new));
    }
}
